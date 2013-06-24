package com.mss.android.web.repositories;

import com.mss.android.web.dtos.Dto;

public class GenericWebRepository<T extends Dto> {
	private WebConnection connection;
	public GenericWebRepository(WebConnection connection){
		this.connection = connection;
	}
	
	public T[] find(){
		return null;
	}
}
