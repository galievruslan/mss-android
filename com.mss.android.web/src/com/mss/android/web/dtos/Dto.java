package com.mss.android.web.dtos;

public abstract class Dto {
	private int id;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	private Boolean isValid;
	
	public Boolean getIsValid(){
		return isValid;
	}
	
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
