package com.mss.android.web.dtos;

@IDtoSource(url = "synchronization/customers.json")
public class Customer extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}
}
