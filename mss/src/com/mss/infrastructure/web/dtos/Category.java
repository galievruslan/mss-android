package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class Category extends Dto {
	
	private String name;
	
	public String getName(){
		return name;
	}	
	
	@SerializedName("parent_id")
	private long parentId;
	
	public long getParentId(){
		return parentId;
	}	
}
