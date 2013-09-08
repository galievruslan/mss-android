package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class Manager extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}
	
	@SerializedName("default_warehouse_id")
	private int defaultWarehouseId;
	
	public int getDefaultWarehouseId(){
		return defaultWarehouseId;
	}
}
