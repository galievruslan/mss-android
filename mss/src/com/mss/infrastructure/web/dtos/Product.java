package com.mss.infrastructure.web.dtos;

public class Product extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}

	private int categoryId;
	
	public int getCategoryId(){
		return categoryId;
	}
}
