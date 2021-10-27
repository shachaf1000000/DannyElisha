package cuponproject.allthedbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import cuponproject.allthebeans.Company;
import cuponproject.alltheconnectionpool.ConnectionPool;
import cuponproject.allthedao.CompaniesDAO;

public class CompaniesdbDAO implements CompaniesDAO {
	private Connection con;
	private int loginID = 0;

	public int getLoginID() {
		return loginID;
	}

	public void setLoginID(int loginID) {
		this.loginID = loginID;
	}
	private static CompaniesdbDAO instance = null;

	public CompaniesdbDAO() {
	}

	public static CompaniesdbDAO getInstance() {
		if (instance == null) {
			instance = new CompaniesdbDAO();
		}
		return instance;
	}
	@SuppressWarnings("finally")
	@Override
	public boolean isCompanyExists(String email, String password) {
		Connection con = null;
		boolean status = false;
		try {
			// log
			System.out.println("connecting to db...");
			con = ConnectionPool.getSingeltonInstance().getConnection();
			// log
			System.out.println("connected to db.");
			String sql = "select * from `java project - coupon system`.companies where email = " + email + " and password = " + password;

			// log
			System.out.println("checking if the company exists...");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs != null) {
				// log
				System.out.println("company exists.");
				status = true;
			}
			// log
			System.out.println("A company starting with email " + email + " does not exist.");

		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
			return status;
		}
	}

	@Override
	public void addCompany(Company company) throws SQLException {
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();
			String createSQL = "INSERT INTO `java project - coupon system`.companies(NAME, EMAIL, PASSWORD)"
					+ " VALUES (?,?,?)";
			PreparedStatement pStatement = con.prepareStatement(createSQL);
			pStatement.setString(1, company.getName());
			pStatement.setString(2, company.getEmail());
			pStatement.setString(3, company.getPassword());
			pStatement.executeUpdate();
			System.out.println("a new Company was created in the database.");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Error encountered while attempting to create a new company.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
	}

	@Override
	public void updateCompany(Company company) throws SQLException {
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();

			String updateSQL = "UPDATE `java project - coupon system`.companies SET PASSWORD=?, EMAIL=?, NAME=? WHERE ID=?";
			PreparedStatement pStatement = con.prepareStatement(updateSQL);
			pStatement.setString(1, company.getPassword());
			pStatement.setString(2, company.getEmail());
			pStatement.setString(3, company.getName());
			pStatement.setLong(4, company.getId());
			pStatement.execute();

			System.out.println("Company " + company.getName() + " was updated!");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Error encountered while attempting to update company.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCompany(Company company) throws SQLException {
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();

			unlinkAllCompanyCoupon(company.getId());

			String removeSQL1 = "DELETE FROM `java project - coupon system`.companies WHERE ID=?";
			PreparedStatement pStatement1 = con.prepareStatement(removeSQL1);
			pStatement1.setLong(1, company.getId());
			pStatement1.executeUpdate();
			System.out.println(
					"Company " + company.getName() + " ID " + company.getId() + " was removed from the database.");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Error encountered while attempting to remove company from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
	}

	public void linkCompanyCoupon(int companyId, int couponId) throws SQLException {
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();

			String linkSQL = "INSERT INTO `java project - coupon system`.coupons_vs_customers VALUES(?,?)";
			PreparedStatement pStatement = con.prepareStatement(linkSQL);
			pStatement.setInt(1, companyId);
			pStatement.setInt(2, couponId);
			pStatement.executeUpdate();
			System.out.println("Company " + companyId + " was linked with coupon " + couponId);
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to link Company with the new coupon.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
	}

	public void unlinkAllCompanyCoupon(int companyId) throws SQLException {
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();

			String linkSQL = "DELETE FROM `java project - coupon system`.coupons_vs_customers WHERE 'COMPANY_ID'=?";
			PreparedStatement pStatement = con.prepareStatement(linkSQL);
			pStatement.setLong(1, companyId);
			pStatement.executeUpdate();
			System.out.println("Company " + companyId + " was unlinked from its coupons");

		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to unlink Company from its Coupons");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
	}

	@Override
	public Collection<Company> getAllCompanies() throws SQLException {
		Collection<Company> companies = new ArrayList<>();
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();
			String getAllSQL = "SELECT * FROM `java project - coupon system`.companies ";
			PreparedStatement pStatement = con.prepareStatement(getAllSQL);
			ResultSet result = pStatement.executeQuery();

			while (result.next()) {
				Company comp = new Company();
				comp.setId(result.getInt(1));
				comp.setName(result.getString(2));
				comp.setPassword(result.getString(3));
				comp.setEmail(result.getString(4));
				companies.add(comp);
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException(
					"Error encountered while attempting to retrieve " + "all companies from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) throws SQLException {
		Company company = new Company();
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();
			String getSQL = "SELECT * FROM `java project - coupon system`.companies WHERE ID=?";
			PreparedStatement pStatement = con.prepareStatement(getSQL);
			pStatement.setInt(1, companyID);
			ResultSet result = pStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					company.setName(result.getString("NAME"));
					company.setPassword(result.getString("PASSWORD"));
					company.setEmail(result.getString("EMAIL"));
					company.setId(result.getInt("ID"));
				}
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Error encountered while attempting to retrieve company from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
		return company;
	}
	public boolean login(String email, String password) throws SQLException, Exception {
		String dbPassword = null;
		try {
			con = ConnectionPool.getSingeltonInstance().getConnection();

			String loginSQL = "SELECT * FROM `java project - coupon system`.companies WHERE NAME=?";
			PreparedStatement pStatement = con.prepareStatement(loginSQL);
			pStatement.setString(1, email);
			ResultSet result = pStatement.executeQuery();

			while (result.next()) {
				dbPassword = result.getString("PASSWORD");
				if (password.equals(dbPassword)) {
					setLoginID(result.getInt("ID"));
					System.out.println("Login success!");
					return true;
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Login FAILED !");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(con);
		}
		return false;
	}
}
