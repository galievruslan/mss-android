package com.mss.infrastructure.web;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;

import com.mss.infrastructure.web.dtos.Dto;

public abstract class WebRepository<TDto extends Dto> {
	
	protected WebConnection connection;
	protected WebRepository(WebConnection connection){
		this.connection = connection;
	}
	
	public abstract List<TDto> find(List<NameValuePair> params) throws URISyntaxException, Exception;
}
