package com.mss.android.web.repositories;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.android.web.dtos.Dto;

public class GenericWebRepository<T extends Dto> {
	private WebConnection connection;
	private String url;
	public GenericWebRepository(WebConnection connection, String url){
		this.connection = connection;
		this.url = url;
	}
	
	public T[] find(Map<String,String> params) throws Exception{		
		String json = connection.getWebServer().Get(url, params);
		System.out.print(json);
		
		Type listType = new TypeToken<T[]>() {}.getType();
		T[] dtos = new Gson().fromJson(json, listType);
		
		return dtos;
	}
}
