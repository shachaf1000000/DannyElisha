package cuponproject.allthedbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import cuponproject.allthebeans.Category;
import cuponproject.allthebeans.Coupon;
import cuponproject.alltheconnectionpool.ConnectionPool;
import cuponproject.allthedao.CuponsDAO;

public class CuponsdbDAO implements CuponsDAO {
	private Connection connection;
	@Override
	public void addCoupon(Coupon coupon) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String createSQL = "INSERT INTO `java project - coupon system`" + "(ID_COMPANY, ID_CATEGORY, TITLE, DESCRIPTION, DATE_START, DATE_END,"
					+ "AMOUNT, PRICE, IMAGE) " + 
					"VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement pStatement = connection.prepareStatement
											(createSQL, Statement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, coupon.getCompanyID());
			pStatement.setInt(2, coupon.getCategory().ordinal());
			pStatement.setString(3, coupon.getTitle());
			pStatement.setString(4, coupon.getDescription());
			pStatement.setDate(5, new java.sql.Date(coupon.getStartDate().getTime()));
			pStatement.setDate(6, new java.sql.Date(coupon.getEndDate().getTime()));
			pStatement.setInt(7, coupon.getAmount());
			pStatement.setDouble(8, coupon.getPrice());
			pStatement.setString(9, coupon.getImage());
			
			int affectedRows = pStatement.executeUpdate();
		
			if (affectedRows == 0) {
				throw new SQLException("Failed to create Coupon.");
			}
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				coupon.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Create Coupon FAILED, No ID Was Obtained.");
			}
			System.out.println("Coupon " + coupon.getTitle() + " was created!");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Creating a new Coupon has failed!");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String updateSQL = "UPDATE `java project - coupon system` SET TITLE=?, START_DATE=?, "
					+ "END_DATE=?, AMOUNT=?, MESSAGE=?, PRICE=? WHERE ID=?";
			PreparedStatement pStatement = connection.prepareStatement(updateSQL);
			pStatement.setString(1, coupon.getTitle());
			pStatement.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pStatement.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pStatement.setInt(4, coupon.getAmount());
			pStatement.setString(5, coupon.getDescription());
			pStatement.setDouble(6, coupon.getPrice());
			pStatement.setInt(7, coupon.getId());
			int affectedRows = pStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Failed to update Coupon.");
			}
			System.out.println("Coupon " + coupon.getTitle() + " was updated!");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Update Coupon FAILED");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}

	@Override
	public void deleteCoupon(Coupon coupon) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String removeSQL = "DELETE FROM `java project - coupon system` WHERE ID=?";
			PreparedStatement pStatement3 = connection.prepareStatement(removeSQL);
			pStatement3.setInt(1, coupon.getId());
			pStatement3.executeUpdate();
			System.out.println("Coupon" + coupon.getTitle() + "was removed from the database.");

		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to remove Coupon from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		
	}
	
	@Override
	public void removeCompanyCoupon(int couponId) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String linkSQL = "DELETE FROM `java project - coupon system`.categories WHERE COUPON_ID=?";
			PreparedStatement pStatement = connection.prepareStatement(linkSQL);
			pStatement.setInt(1, couponId);
			pStatement.executeUpdate();
			System.out.println("Coupon " + couponId + " was deleted and unlinked "
									+ "from all companies");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to remove Coupon from all Companies.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}
	public void removeCustomerCoupon(int couponId) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String linkSQL = "DELETE FROM `java project - coupon system`.coupons_vs_customers WHERE COUPON_ID=?";
			PreparedStatement pStatement = connection.prepareStatement(linkSQL);
			pStatement.setInt(1, couponId);
			pStatement.executeUpdate();
			System.out.println("Coupon " + couponId + " was deleted and unlinked "
									+ "from all customers");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to remove Coupon from all Customers.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}
	@Override
	public Collection<Coupon> getAllCoupons() throws SQLException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();

		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String getAllSQL = "SELECT ID FROM `java project - coupon system`.coupons";
			PreparedStatement pStatement = connection.prepareStatement(getAllSQL);
			ResultSet result = pStatement.executeQuery();

			if (result != null) {
				while (result.next()) {
					Coupon coupon = getCoupon(result.getInt("ID"));
					coupons.add(coupon);
				}
				System.out.println("All coupons retrieved from the database!");
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to retrieve all coupons from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return coupons;
	}

	@Override
	public Coupon getCoupon(int couponID) throws SQLException {
		Coupon coupon = new Coupon();
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String getSQL = "SELECT * FROM `java project - coupon system`.coupons WHERE ID=?";
			PreparedStatement pStatement = connection.prepareStatement(getSQL);
			pStatement.setInt(1, couponID);
			ResultSet result = pStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					coupon.setTitle(result.getString("TITLE"));
					coupon.setStartDate(result.getDate("START_DATE"));
					coupon.setEndDate(result.getDate("END_DATE"));
					coupon.setAmount(result.getInt("AMOUNT"));
					coupon.setCategory(Category.valueOf(result.getString("CATEGORY")));
					coupon.setDescription(result.getString("DESCRIPTION"));
					coupon.setPrice(result.getInt("PRICE"));
					coupon.setImage(result.getString("IMAGE"));
					coupon.setId(couponID);				
				}
			}
			else{
				throw new SQLException("Coupon was not found in the database.");
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to retrieve Coupon from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return coupon;
	}
	public Collection<Coupon> getCompanyCoupons(int companyId) throws SQLException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String getCompanyCouponsSQL = "SELECT COUPON_ID FROM `java project - coupon system`.coupons_vs_customers"
					+ " WHERE COMPANY_ID = ?";
			PreparedStatement pStatement = connection.prepareStatement(getCompanyCouponsSQL);
			pStatement.setInt(1, companyId);
			ResultSet result = pStatement.executeQuery();
			System.out.println(result);
			if (result != null) {
				while (result.next()) {
					Coupon coupon = getCoupon(result.getInt(1));
					coupons.add(coupon);
				}
			System.out.println("Company ID-" + companyId + " : all coupons retrieved!");
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException();
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return coupons;
	}
	@Override
	public void addCouponPurchase(int customerID, int couponID) throws InterruptedException {
		 // log
        System.out.println("connecting to db...");
        Connection con = ConnectionPool.getSingeltonInstance().getConnection();

        // log
        System.out.println("connected to db.");
        String sql = "INSERT INTO `java project - coupon system`.coupons_vs_customers (customer_id,coupon_id)\r\n" + "VALUES("
                + customerID + ", " + couponID + ");";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionPool.getSingeltonInstance().restoreConnection(con);
		
	}

	@Override
	public void deleteCouponPurcahse(int customerID, int couponID) throws InterruptedException {
		// log
        System.out.println("connecting to db...");
        Connection con = ConnectionPool.getSingeltonInstance().getConnection();
        // log
        System.out.println("connected to db.");
        String sql = "DELETE FROM `java project - coupon system`.coupons_vs_customers (customer_id,coupon_id)\r\n" + "VALUES("
                + customerID + ", " + couponID + ");";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionPool.getSingeltonInstance().restoreConnection(con);
    }
	public Date getTime() {
		Date today = new Date();
		today = Calendar.getInstance().getTime();
		return (Date) today;
	}
	public void removeExpiredCoupon() throws SQLException {
		Collection<Coupon> taskList = null;
		CuponsdbDAO couponTask = new CuponsdbDAO();
		try {
			taskList = (ArrayList<Coupon>) couponTask.getAllCoupons();
		} catch (SQLException e) {
			throw new SQLException("Failed to remove expired Coupons.");
		}
		Iterator<Coupon> iter = taskList.iterator();
		if (iter != null) {
			while (iter.hasNext()) {
				Coupon currentCoupon = iter.next();
				if (!currentCoupon.getEndDate().after(getTime())) {
					try {
						couponTask.deleteCoupon(currentCoupon);
					} catch (SQLException e) {
						throw new SQLException("Failed to remove expired Coupons.");
					}

				}
			}
		}
		
	}

}
