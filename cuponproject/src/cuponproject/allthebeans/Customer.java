package cuponproject.allthebeans;

import java.util.ArrayList;

public class Customer 
{
	int id;
	String firstName;
	String lastName;
	String email;
	String password;
	ArrayList<Coupon> coupons= new ArrayList<Coupon>();
	public Customer(int id, String firstName, String lastName, String email, String password,
			ArrayList<Coupon> coupons) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}
	

}
