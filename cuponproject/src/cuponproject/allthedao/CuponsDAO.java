package cuponproject.allthedao;

import java.sql.SQLException;
import java.util.Collection;

import cuponproject.allthebeans.Coupon;


public interface CuponsDAO 
{	
	 void addCoupon(Coupon coupon) throws SQLException;
	
	 void updateCoupon(Coupon coupon) throws SQLException;
	
	 void deleteCoupon(Coupon coupon) throws SQLException;
	
	 Collection<Coupon> getAllCoupons() throws SQLException;
	
	 Coupon getCoupon(int couponID) throws SQLException;
	
	 void addCouponPurchase(int customerID, int couponID)throws SQLException, InterruptedException; 
	
	 void deleteCouponPurcahse(int customerID, int couponID) throws SQLException, InterruptedException;

	void removeCompanyCoupon(int couponId) throws SQLException;
	







}
