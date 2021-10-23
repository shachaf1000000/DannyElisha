package com.group6.couponSystem.DAOs;

import java.util.ArrayList;

import com.group6.couponSystem.beans.Coupon;

public interface CouponsDAO {
	public boolean isCouponExists(Coupon coupon);
	public void addCoupon(Coupon company);
	public void updateCoupon(Coupon company);
	public void deleteCoupon(int couponID);
	public ArrayList<Coupon> getAllCoupons();
	public Coupon getOneCoupon(int couponID);
	public void addCouponPurchase(int customerID, int couponID);
	public void deleteCouponPurchase(int customerID, int couponID);
}
