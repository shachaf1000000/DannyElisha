package com.example.c_p.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Coupon 
{
	@Id
	@GeneratedValue
	int id;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	int companyId;
	static Category category;
	String title;
	String description;
	Date startDate;
	Date endDate;
	int amount;
	double price;
	String image;

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "customers_coupons", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	List<Customer> customers= new ArrayList<Customer>();
	public Coupon(int id, int companyId, String title, String description, Date startDate, Date endDate, int amount,
			double price, String image, List<Customer> customers) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.customers = customers;
	}
	public Coupon( int companyId, String title, String description, Date startDate, Date endDate, int amount,
			double price, String image, List<Customer> customers) {
		super();
		
		this.companyId = companyId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.customers = customers;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public static Category getCategory() {
		return category;
	}
	public static void setCategory(Category category) {
		Coupon.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, companyId, customers, description, endDate, id, image, price, startDate, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		return amount == other.amount && companyId == other.companyId && Objects.equals(customers, other.customers)
				&& Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
				&& id == other.id && Objects.equals(image, other.image)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + companyId + ", title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price
				+ ", image=" + image + ", customers=" + customers + "]";
	}
	
}
