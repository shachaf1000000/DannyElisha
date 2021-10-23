package com.group6.couponSystem.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.group6.couponSystem.beans.Coupon;
import com.group6.couponSystem.dbManager.ConnectionPool;

public class CouponsDBDAO implements CouponsDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static CouponsDBDAO instance = null;

	private CouponsDBDAO() {
	}

	public static CouponsDBDAO getInstance() {
		if (instance == null) {
			instance = new CouponsDBDAO();
		}
		return instance;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean isCouponExists(Coupon coupon) {
		Connection connection = null;
		boolean status = false;
		try {
			// log
			System.out.println("connecting to db...");
			connection = connectionPool.getConnection();
			// log
			System.out.println("connected to db.");
			String sql = "select * from couponsystem.coupons where coupon_id = " + coupon.getId() + ";";

			// log
			System.out.println("checking if the coupon exists...");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs != null) {
				// log
				System.out.println("coupon exists.");
				return true;
			} else
				return false;
		} finally {
			connectionPool.restoreConnection(connection);
			System.out.println("The coupon " + coupon+ " does not exist.");
			return status;
		}
		// log

	}

	@Override
	public void addCoupon(Coupon coupon) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		if (!(isCouponExists(coupon))) {
			String sql = "INSERT INTO couponsystem.customers (id,company_id,category_id,title,description,start_date,end_date,amount,price,image)\r\n"
					+ "VALUES(" + coupon.getId() + "," + coupon.getCompanyID() + ",'" + coupon.getCategory() + "','"
					+ coupon.getTitle() + "','" + coupon.getDescription() + "'," + coupon.getStartDate() + ","
					+ coupon.getEndDate() + "," + coupon.getAmount() + "," + coupon.getPrice() + ",'"
					+ coupon.getImage() + "');";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("coupon already exists in system.");
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		if (isCouponExists(coupon)) {
			String sql = "UPDATE Cuopons\r\n" + "SET company_id = " + coupon.getCompanyID() + ", category_id "
					+ coupon.getCategory() + ", title = " + coupon.getTitle() + ", description = "
					+ coupon.getDescription() + ", start_date = " + coupon.getStartDate() + ", end_date"
					+ coupon.getEndDate() + ", amount" + coupon.getAmount() + ", price" + coupon.getPrice() + ", image"
					+ coupon.getImage() + ");" + "WHERE ID = " + coupon.getId() + ";";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("coupon does not exist, cannot update.");
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public void deleteCoupon(int couponID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "DELETE FROM Coupons WHERE ID = " + couponID + ";";
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
	public ArrayList<Coupon> getAllCoupons() {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "SELECT * FROM COUPONS;";
		;
		int id = 0;
		int companyID = 0;
		int categoryID = 0;
		String title = "";
		String description = "";
		Date startDate = null;
		Date endDate = null;
		int amount = 0;
		double price = 0;
		String image = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("ID");
				companyID = rs.getInt("company_id");
				categoryID = rs.getInt("category_ID");
				title = rs.getString("title");
				description = rs.getString("description");
				startDate = rs.getDate("start_date");
				endDate = rs.getDate("end_date");
				amount = rs.getInt("amount");
				price = rs.getDouble("price");
				image = rs.getString("image");
				coupons.add(new Coupon(id, companyID, categoryID, title, description, startDate, endDate, amount, price,
						image));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// log
		System.out.println("all coupons returned.");
		connectionPool.restoreConnection(connection);
		return coupons;
	}

	@Override
	public Coupon getOneCoupon(int couponID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "SELECT * FROM COUPONS WHERE ID = " + couponID + ";";
		;
		int id = 0;
		int companyID = 0;
		int categoryID = 0;
		String title = "";
		String description = "";
		Date startDate = null;
		Date endDate = null;
		int amount = 0;
		double price = 0;
		String image = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt("ID");
				companyID = rs.getInt("company_id");
				categoryID = rs.getInt("category_ID");
				title = rs.getString("title");
				description = rs.getString("description");
				startDate = rs.getDate("start_date");
				endDate = rs.getDate("end_date");
				amount = rs.getInt("amount");
				price = rs.getDouble("price");
				image = rs.getString("image");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Coupon coupon = new Coupon(id, companyID, categoryID, title, description, startDate, endDate, amount, price,
				image);
		// log
		System.out.println("coupon returned.");
		connectionPool.restoreConnection(connection);
		return coupon;
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "INSERT INTO couponsystem.customers_vs_coupons (customer_id,coupon_id)\r\n" + "VALUES("
				+ customerID + ", " + couponID + ");";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionPool.restoreConnection(connection);
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) {
		// log
		System.out.println("connecting to db...");
		Connection connection = connectionPool.getConnection();
		// log
		System.out.println("connected to db.");
		String sql = "DELETE FROM couponsystem.customers_vs_coupons (customer_id,coupon_id)\r\n" + "VALUES("
				+ customerID + ", " + couponID + ");";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionPool.restoreConnection(connection);
	}

}
