package com.mss.infrastructure.web.dtos;

public class Category extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}	
	
	private int parentId;
	
	public int getParentId(){
		return parentId;
	}	
}
