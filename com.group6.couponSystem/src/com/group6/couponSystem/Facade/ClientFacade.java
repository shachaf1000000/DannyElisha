package com.group6.couponSystem.Facade;

import com.group6.couponSystem.DAOs.*;

public abstract class ClientFacade {
	protected CompaniesDAO companyDAO = CompaniesDBDAO.getInstance();
	protected CustomersDAO customerDAO = CustomersDBDAO.getInstance() ;	
	protected CouponsDAO couponDAO = CouponsDBDAO.getInstance();
	
	public boolean login(String email, String password) {
		return false;
	}
	
}
