package com.mss.infrastructure.web.dtos;

public class Customer extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}
	
	private String address;
	
	public String getAddress(){
		return address;
	}
	
	private double debt;
	
	public double getDebt(){
		return debt;
	}
}
