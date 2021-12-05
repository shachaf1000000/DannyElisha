package com.example.c_p.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.c_p.repository.CompanyRepository;
import com.example.c_p.repository.CouponRepository;
import com.example.c_p.repository.CustomerRepository;
@Service
public abstract class ClientService {
	@Autowired
	 protected CompanyRepository companyRepository;
	 @Autowired
	 protected CustomerRepository customerRepository;
	 @Autowired
	 protected CouponRepository couponRepository;
	 public abstract boolean login(String email,String password);
}
