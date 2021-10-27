package cuponproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import cuponproject.allthebeans.Category;
import cuponproject.allthebeans.Coupon;
import cuponproject.alltheconnectionpool.ConnectionPool;
import cuponproject.allthedbdao.CuponsdbDAO;

public class Main {
	private final static String URL = "jdbc:mysql://localhost:3306/java project - coupon system?createDatabaseIfNotExist=TRUE";
	private final static String USER = "root";
	private final static String PASSWORD = "1234";
	static Connection con;
	static CuponsdbDAO cuponsdbDAO;
	public static void main(String[] args) {
		Date startDate = new Date(System.currentTimeMillis());
		Date endDate = Date.valueOf("2021-10-30");
//		try {
//			Connection con = DriverManager.getConnection("65","yo","64564");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(con);
		
		try {
			//con = DriverManager.getConnection(URL,USER,PASSWORD);
			CuponsdbDAO cdbCuponsdbDAO = new CuponsdbDAO();
			cdbCuponsdbDAO.addCoupon(new Coupon(565,Category.FOOD,"titleforcoupon", "descriptionforcoupon", startDate, endDate, 59, 85.5, "ut"));
			//64, 89, Category.FOOD, "titleforcoupon", "descriptionforcoupon", startDate, endDate, 59, 85.5, "ut")
			System.out.println(cdbCuponsdbDAO);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
//	
//		cuponsdbDAO.addCoupon(coupon);
	}
	

}
