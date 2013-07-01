package com.mss.android.web.repositories;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.mss.android.web.dtos.Dto;

public abstract class GenericWebRepository<T extends Dto> {
	
	protected WebConnection connection;
	protected String url;
	protected GenericWebRepository(WebConnection connection, String url){
		this.connection = connection;
		this.url = url;
	}
	
	public abstract List<T> find(Map<String,String> params) throws URISyntaxException, Exception;
}
