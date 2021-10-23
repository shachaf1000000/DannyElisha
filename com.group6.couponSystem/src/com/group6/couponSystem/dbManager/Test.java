package com.group6.couponSystem.dbManager;

import com.group6.couponSystem.DAOs.CustomersDBDAO;
import com.group6.couponSystem.beans.Customer;

public class Test {

	public static void main(String[] args) {
		ConnectionPool.getInstance();
		CustomersDBDAO customerDAO = new CustomersDBDAO();
		Customer customer1 = new Customer(1,"veronika","kri","v.k@g.com","1234");
		customerDAO.isCustomerExists(customer1.getEmail(), customer1.getPassword());
		System.out.println(customer1);
		customerDAO.isCustomerExists(customer1.getEmail(), customer1.getPassword());
	}

}
