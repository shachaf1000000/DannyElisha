package com.example.c_p.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.c_p.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	Company findByEmailAndPassword(String email,String password);
}
