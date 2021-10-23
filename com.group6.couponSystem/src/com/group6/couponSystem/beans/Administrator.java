package com.group6.couponSystem.beans;

import java.util.ArrayList;

public class Administrator {

	private static Administrator instance = null;
	private String name;
	private String email = "Admin@admin.com";
	private String password = "admin";
	private ArrayList<Company> companies = new ArrayList<>();
	private ArrayList<Customer> customers = new ArrayList<>();

	private Administrator() {
	}

	public static Administrator getInstance() {
		if (instance == null) {
			instance = new Administrator();
		}
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(ArrayList<Company> companies) {
		this.companies = companies;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
	
	

}
