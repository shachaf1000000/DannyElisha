package com.example.c_p.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.c_p.repository.CompanyRepository;
import com.example.c_p.repository.CouponRepository;
import com.example.c_p.repository.CustomerRepository;

@Service
@Transactional
public class ServiceAdmin extends ClientService{

	@Override
	public boolean login(String email, String password) {
		if (email.equalsIgnoreCase("Admin@gmail.com")&&password.equals("Admin1234")) {
			return true;
		}
		return false;
	}
 

}
