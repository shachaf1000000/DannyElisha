package com.group6.couponSystem.Facade;

import com.group6.couponSystem.beans.Administrator;
import com.group6.couponSystem.beans.Company;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
	}

	@Override
	public boolean login(String email, String password) {
		if (Administrator.getInstance().getEmail().equals(email)
				&& Administrator.getInstance().getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public void addCompany(Company company) {
		for (Company comp : Administrator.getInstance().getCompanies()) {
			if (company.getEmail().equals(comp.getEmail()) || company.getName().equals(comp.getName())) {
				System.out.println("Sorry, company already exists!");
				return;
			}
			
		}
		System.out.println("Company added successfully ");
		Administrator.getInstance().getCompanies().add(company);
		companyDAO.addCompany(company);
	}
	
	
	public
}
