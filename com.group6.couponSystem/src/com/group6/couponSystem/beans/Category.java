package com.group6.couponSystem.beans;

public enum Category {
	Food(1),Electricity(2),Vacation(3),Restaurant(4);

	public int id;
	
	Category(int id) {
		this.id = id;
	}
}
