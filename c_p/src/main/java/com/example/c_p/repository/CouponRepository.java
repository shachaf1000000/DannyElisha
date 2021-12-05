package com.example.c_p.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.c_p.beans.Coupon;


public interface CouponRepository extends JpaRepository<Coupon,Integer>{

}
