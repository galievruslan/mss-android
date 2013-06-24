package com.mss.android.web.dtos;

public class ShippingAddress extends Dto {
	private String name;
	
	public String getName(){
		return name;
	}
	
	private String address;
	
	public String getAddress(){
		return address;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	private int customerId;
	
	public int getCustomerId() {
		return customerId;
	}
}
