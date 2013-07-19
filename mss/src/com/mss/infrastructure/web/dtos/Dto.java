package com.mss.infrastructure.web.dtos;

public abstract class Dto {
	private int id;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	private Boolean validity;
	
	public Boolean getIsValid(){
		return validity;
	}
	
	public void setIsValid(Boolean isValid) {
		this.validity = isValid;
	}
}
