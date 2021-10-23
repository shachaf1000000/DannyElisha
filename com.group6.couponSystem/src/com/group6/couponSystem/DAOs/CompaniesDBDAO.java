package com.group6.couponSystem.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.group6.couponSystem.beans.Company;
import com.group6.couponSystem.dbManager.ConnectionPool;

public class CompaniesDBDAO implements CompaniesDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static CompaniesDBDAO instance = null;

	private CompaniesDBDAO() {
	}

	public static CompaniesDBDAO getInstance() {
		if (instance == null) {
			instance = new CompaniesDBDAO();
		}
		return instance;
	}

	@SuppressWarnings("finally")

	public boolean isCompanyExists(String email, String password) {
		Connection connection = null;
		boolean status = false;
		try {
			// log
			System.out.println("connecting to db...");
			connection = connectionPool.getConnection();
			// log
			System.out.println("connected to db.");
			String sql = "select * from couponsystem.companies where email = " + email + " and password = " + password;

			// log
			System.out.println("checking if the company exists...");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs != null) {
				// log
				System.out.println("company exists.");
				status = true;
			}
			// log
			System.out.println("A company starting with email " +email+ " does not exist.");

		} finally {
			connectionPool.restoreConnection(connection);
			return status;
		}
	}

	@Override
	public void addCompany(Company company) {
		// log
		System.out.println("connecting to d...b");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		if (!(isCompanyExists(company.getEmail(), company.getPassword()))) {
			String sql = "INSERT INTO couponsystem.companies (id,NAME,EMAIL,PASSWORD)\r\n" + "VALUES(" + company.getId()
					+ ",'" + company.getName() + "','" + company.getEmail() + "','" + company.getPassword() + "');";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("company already exists.");
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public void updateCompany(Company company) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		if ((!(isCompanyExists(company.getEmail(), company.getPassword())))) {
			System.out.println(
					"can't update as the company does not exist in the database, please add a new company instead.");
		} else {
			String sql = "UPDATE Companies \r\n" + "SET FIRST_NAME = " + company.getName() + ", EMAIL = "
					+ company.getEmail() + ", PASSWORD = " + company.getPassword() + ");" + "WHERE ID = "
					+ company.getId() + ";";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public void deleteCompany(int companyID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "DELETE FROM Companies WHERE ID = " + companyID + ";";
		;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("company deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public ArrayList<Company> getAllCompanies() {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		ArrayList<Company> companies = new ArrayList<Company>();
		String sql = "SELECT * FROM COMPANIES";
		;
		int id = 0;
		String name = "";
		String email = "";
		String password = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				email = rs.getString("email");
				password = rs.getString("password");
				companies.add(new Company(id, name, email, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// log
		System.out.println("all companies returned.");
		connectionPool.restoreConnection(connection);
		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "SELECT * FROM COMPANIES WHERE ID = " + companyID + ";";
		;
		int id = 0;
		String name = "";
		String email = "";
		String password = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				email = rs.getString("email");
				password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Company company = new Company(id, name, email, password);
		// log
		System.out.println("company returned.");
		connectionPool.restoreConnection(connection);
		return company;
	}

}
