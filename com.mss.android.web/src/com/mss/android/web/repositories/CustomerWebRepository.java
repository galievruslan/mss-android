package com.mss.android.web.repositories;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.android.web.dtos.Customer;

public class CustomerWebRepository extends GenericWebRepository<Customer> {
	
	protected CustomerWebRepository(WebConnection connection, String url){
		super(connection, url);
	}
	
	public List<Customer> find(Map<String,String> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().Get(url, params);
		System.out.print(json);
		
		Type listType = new TypeToken<List<Customer>>() {}.getType();
		return new Gson().fromJson(json, listType);
	}
}
