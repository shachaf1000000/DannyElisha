package com.example.c_p.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.c_p.beans.Company;
import com.example.c_p.repository.CompanyRepository;
import com.example.c_p.repository.CouponRepository;
import com.example.c_p.repository.CustomerRepository;

@Service
@Scope("prototype")
@Transactional
public class ServiceCompany extends ClientService {
  private int companyId;
@Override
public boolean login(String email, String password) {
	Company res = companyRepository.findByEmailAndPassword(email, password);
	if (res!=null) {
		companyId=res.getId();
		return true;
	}
	return false;
}


}
