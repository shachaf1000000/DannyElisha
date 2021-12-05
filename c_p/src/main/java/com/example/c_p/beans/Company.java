package com.example.c_p.beans;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company 
{
	@Id
	@GeneratedValue
	int id;
	
	String name;
	String email;
	String password;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "companyId")
	ArrayList<Coupon> coupons= new ArrayList<Coupon>();
	
	public Company() {
		
	}
	public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(coupons, email, id, name, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(coupons, other.coupons) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

}
