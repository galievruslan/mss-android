package com.mss.infrastructure.web.repositories;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.infrastructure.web.WebConnection;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.ProductRemainder;

public class ProductRemainderWebRepository extends WebRepository<ProductRemainder> {
	final String PATH = "/synchronization/remainders.json";
	public ProductRemainderWebRepository(WebConnection connection){
		super(connection);
	}
	
	public List<ProductRemainder> find(List<NameValuePair> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().get(PATH, params);
		
		Type listType = new TypeToken<List<ProductRemainder>>() {}.getType();
		return new Gson().fromJson(json, listType);
	}
}
