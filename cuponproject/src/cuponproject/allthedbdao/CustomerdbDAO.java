package cuponproject.allthedbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import cuponproject.allthebeans.Customer;
import cuponproject.alltheconnectionpool.ConnectionPool;
import cuponproject.allthedao.CustomerDAO;

public class CustomerdbDAO implements CustomerDAO {
	private Connection connection;
	private static CustomerdbDAO instance = null;
	private int loginID = 0;

	public int getLoginID() {
		return loginID;
	}

	public void setLoginID(int loginID) {
		this.loginID = loginID;
	}
	public CustomerdbDAO() {
	}

	public static CustomerdbDAO getInstance() {
		if (instance == null) {
			instance = new CustomerdbDAO();
		}
		return instance;
	}
	@SuppressWarnings("finally")
    @Override
    
    public boolean isCustomerExists(String email, String password) {
        Connection connection = null;
        boolean status = false;
        try {
        // log
        System.out.println("connecting to db...");
        connection = ConnectionPool.getSingeltonInstance().getConnection();
        // log
        System.out.println("connected to db.");
        String sql = "SELECT * FROM `java project - coupon system`.customers  WHERE EMAIL = '" + email + "' AND PASSWORD = '" + password
                + "';";

            // log
            System.out.println("checking if the customer exists...");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (email.equals(rs.getString("EMAIL")) && password.equals(rs.getString("PASSWORD"))) {
                System.out.println("customer exists.");
                return true;
                // fix
            } else
                return false;
        }  finally {
        	ConnectionPool.getSingeltonInstance().restoreConnection(connection);
            System.out.println("A customer that has an email of " + email + " does not exist.");
            return status;
        }

    } 

	@Override
	public void addCustomer (Customer customer)throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String createSQL = "INSERT INTO `java project - coupon system`.customers (LASTNAME,FIRSTNAME,PASSWORD) VALUES (?,?,?)";
			PreparedStatement pStatement = connection.prepareStatement(createSQL);
			pStatement.setString(1, customer.getLastName());
			pStatement.setString(2, customer.getFirstName());
			pStatement.setString(3, customer.getPassword());
			pStatement.executeUpdate();

			System.out.println("Customer " + customer.getLastName() + customer.getFirstName() + " was created!");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to create a new Customer.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		try {
			ConnectionPool.getSingeltonInstance().getConnection();

			String updateSQL = "UPDATE `java project - coupon system`.customers SET LASTNAME=?, FIRSTNAME=?, PASSWORD=? WHERE ID=?";
			PreparedStatement pStatement = connection.prepareStatement(updateSQL);
			pStatement.setString(1, customer.getLastName());
			pStatement.setString(2, customer.getFirstName());
			pStatement.setString(3, customer.getPassword());
			pStatement.setInt(4, customer.getId());
			pStatement.execute();
			System.out.println("Customer " + customer.getLastName()+ customer.getFirstName()  + " was updated!");
			
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to update Customer.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}

	@Override
	public void deleteCustomer(Customer customer) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			unlinkAllCustomerCoupon(customer.getId());

			String removeSQL = "DELETE FROM `java project - coupon system`.customers WHERE ID=?";
			PreparedStatement pStatement2 = connection.prepareStatement(removeSQL);
			pStatement2.setInt(1, customer.getId());
			pStatement2.executeUpdate();
			System.out.println("Customer " + customer.getLastName()+ customer.getFirstName() + " ID "
								+ customer.getId() + " was deleted!");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to remove Customer from the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}

	}
	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException{
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String getAllSQL = "SELECT * FROM `java project - coupon system`.customers";
			PreparedStatement pStatement = connection.prepareStatement(getAllSQL);
			ResultSet result = pStatement.executeQuery();
			if(result != null){
				while (result.next()) {
					Customer customer = new Customer();
					customer.setId(result.getInt(1));
					customer.setLastName(result.getString(2));
					customer.setFirstName(result.getString(3));
					customer.setPassword(result.getString(4));
					customers.add(customer);
				}
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to retrieve all Customers in the database.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return customers;
	}

	@Override
	public Customer getOneCustomer(int CustomerID) throws SQLException {
		Customer customer = new Customer();
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String getSQL = "SELECT * FROM `java project - coupon system`.customers WHERE ID=?";
			PreparedStatement pStatement = connection.prepareStatement(getSQL);
			pStatement.setLong(1, CustomerID);
			ResultSet result = pStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					customer.setLastName(result.getString("LASTNAME"));
					customer.setFirstName(result.getString("FIRSTNAME"));
					customer.setPassword(result.getString("PASSWORD"));
					customer.setId(CustomerID);
				}
			System.out.println("Customer " + customer.getLastName()+ customer.getFirstName() + " was retrieved!");
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to retrieve Customer.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return customer;
	}
	public Collection<Integer> getCustomerCoupons(int CustomerID) throws SQLException {
		Collection<Integer> CustCouponsId = new ArrayList<>();
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String getCouponsSQL = "SELECT ID_COUPON FROM `java project - coupon system`.coupons_vs_customers "
									+ "WHERE ID_CUSTOMER = ?";
			PreparedStatement pStatement = connection.prepareStatement(getCouponsSQL);
			pStatement.setInt(1, CustomerID);
			ResultSet result = pStatement.executeQuery();
			if(result != null){
				while (result.next()) {
					CustCouponsId.add(result.getInt("ID_COUPON"));
				}
			}
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to retrieve Customer's Coupons.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return CustCouponsId;
	}

	
	public void linkCustomerCoupon(int customerId, int couponId) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String linkSQL = "INSERT INTO `java project - coupon system`.coupons_vs_customers (CUSTOMER_ID , COUPON_ID)"
								+ " values (?,?)";
			PreparedStatement pStatement = connection.prepareStatement(linkSQL);
			pStatement.setLong(1, customerId);
			pStatement.setLong(2, couponId);
			pStatement.executeUpdate();
			System.out.println("Customer " + customerId + " was linked with coupon " + couponId);
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to link Customer and Coupon.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}

	
	public void unlinkAllCustomerCoupon(int customerId) throws SQLException {
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();

			String linkSQL = "DELETE FROM `java project - coupon system`.coupons_vs_customers WHERE 'CUSTOMER_ID'=?";
			PreparedStatement pStatement = connection.prepareStatement(linkSQL);
			pStatement.setLong(1, customerId);
			pStatement.executeUpdate();
			System.out.println("Customer " + customerId + " was unlinked from all coupons");
		} catch (SQLException | InterruptedException e) {
			throw new SQLException("Failed to unlink Customer from its Coupons.");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
	}

	public boolean login(String email, String password) throws SQLException, InterruptedException {
		String dbPassword = null;
		try {
			connection = ConnectionPool.getSingeltonInstance().getConnection();
			String loginSQL = "SELECT * FROM `java project - coupon system`.customers WHERE NAME=?";
			PreparedStatement pStatement = connection.prepareStatement(loginSQL);
			pStatement.setString(1, email);
			ResultSet result = pStatement.executeQuery();
			if(result != null){
				while (result.next()) {
					dbPassword = result.getString("PASSWORD");
					if (password.equals(dbPassword)) {
						setLoginID(result.getInt("ID"));
						return true;
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Login FAILED !");
		} finally {
			ConnectionPool.getSingeltonInstance().restoreConnection(connection);
		}
		return false;
	}

	
}
