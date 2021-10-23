package com.group6.couponSystem.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.group6.couponSystem.beans.Customer;
import com.group6.couponSystem.dbManager.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static CustomersDBDAO instance = null;

	private CustomersDBDAO() {
	}

	public static CustomersDBDAO getInstance() {
		if (instance == null) {
			instance = new CustomersDBDAO();
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
		connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "SELECT * FROM couponsystem.customers WHERE EMAIL = '" + email + "' AND PASSWORD = '" + password
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
			connectionPool.restoreConnection(connection);
			System.out.println("A customer that has an email of " + email + " does not exist.");
			return status;
		}

	}

	@Override
	public void addCustomer(Customer customer) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		if (!(isCustomerExists(customer.getEmail(), customer.getPassword()))) {
			String sql = "INSERT INTO couponsystem.customers (ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD)\r\n" + "VALUES("
					+ customer.getId() + ",'" + customer.getFirstName() + "','" + customer.getLastName() + "','"
					+ customer.getEmail() + "','" + customer.getPassword() + "');";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				System.err.println("customer already exists.");
			}
			connectionPool.restoreConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
			String sql = "UPDATE Customers\r\n" + "SET FIRST_NAME = '" + customer.getFirstName() + "', LAST_NAME = '"
					+ customer.getLastName() + "', EMAIL = '" + customer.getEmail() + "', PASSWORD = '"
					+ customer.getPassword() + "' " + "WHERE ID = " + customer.getId() + ";";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(
					"can't update as the customer does not exist in the database, please add a new customer instead.");
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public void deleteCustomer(int customerID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "DELETE FROM Customers WHERE ID = " + customerID + ";";
		;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public ArrayList<Customer> getAllCustomers() {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		ArrayList<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT * FROM CUSTOMERS ;";
		;
		int id = 0;
		String firstName = "";
		String lastName = "";
		String email = "";
		String password = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("ID");
				firstName = rs.getString("FIRST_NAME");
				lastName = rs.getString("LAST_NAME");
				email = rs.getString("EMAIL");
				password = rs.getString("PASSWORD");
				customers.add(new Customer(id, firstName, lastName, email, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// log
		System.out.println("all customers returned.");
		connectionPool.restoreConnection(connection);
		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "SELECT * FROM CUSTOMERS WHERE ID = " + customerID + ";";
		;
		int id = 0;
		String firstName = "";
		String lastName = "";
		String email = "";
		String password = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("ID");
				firstName = rs.getString("FIRST_NAME");
				lastName = rs.getString("LAST_NAME");
				email = rs.getString("EMAIL");
				password = rs.getString("PASSWORD");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Customer customer = new Customer(id, firstName, lastName, email, password);
		// log
		System.out.println("customer returned.");
		connectionPool.restoreConnection(connection);
		return customer;
	}

}
