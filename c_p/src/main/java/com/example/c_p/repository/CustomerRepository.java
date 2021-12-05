package com.example.c_p.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.c_p.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
