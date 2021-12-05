package com.example.c_p.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.c_p.repository.CouponRepository;
import com.example.c_p.repository.CustomerRepository;

@Service
@Transactional
public class ServiceCustomer extends ClientService{

	
// @Autowired
// private CompanyRepository companyRepository;
 @Autowired
 private CustomerRepository customerRepository;
 @Autowired
 private CouponRepository couponRepository;
@Override
public boolean login(String email, String password) {
	// TODO Auto-generated method stub
	return false;
}



}
