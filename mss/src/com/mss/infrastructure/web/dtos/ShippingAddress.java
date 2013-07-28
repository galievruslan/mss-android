package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

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

	@SerializedName("customer_id")
	private long customerId;
	
	public long getCustomerId() {
		return customerId;
	}
}
