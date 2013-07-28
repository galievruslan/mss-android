package com.mss.infrastructure.web.dtos;

public abstract class Dto {	
	private long id;
	
	public long getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	private Boolean validity;
	
	public Boolean getIsValid(){
		return validity;
	}
	
	protected void setIsValid(Boolean value){
		validity = value;
	}
}
