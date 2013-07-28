package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class Product extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}

	@SerializedName("category_id")
	private long categoryId;
	
	public long getCategoryId(){
		return categoryId;
	}
}
