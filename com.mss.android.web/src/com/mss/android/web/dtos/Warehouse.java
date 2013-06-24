package com.mss.android.web.dtos;

public class Warehouse extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}

	private String address;
	
	public String getAddress(){
		return address;
	}
	
	private Boolean isDefault;
	
	public Boolean getIsDefault(){
		return isDefault;
	}
}
