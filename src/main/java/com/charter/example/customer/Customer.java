package com.charter.example.customer;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int month;
	private int amount;
	private int rewardsPoint;

	public Customer() {
		super();
	}
	
	public Customer(Long id, String name, int month, int amt) {
		super();
		this.id = id;
		this.name = name;
		this.month = month;
		this.amount = amt;
	}

	public Customer(String name, int month, int amt) {
		super();
		this.name = name;
		this.month = month;
		this.amount = amt;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getRewardsPoint() {
		return rewardsPoint;
	}

	public void setRewardsPoint(int rewardsPoint) {
		this.rewardsPoint = rewardsPoint;
	}

	@Override
	public String toString() {
		return String.format("Customer [id=%s, name=%s, month=%s, amount=%s, points=%s]", id, name, month, amount, rewardsPoint);
	}

}
