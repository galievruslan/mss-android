package com.mss.infrastructure.web.repositories;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.infrastructure.web.WebConnection;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.UnitOfMeasure;

public class UnitOfMeasureWebRepository extends WebRepository<UnitOfMeasure> {
	final String PATH = "synchronization/unit_of_measures.json";
	public UnitOfMeasureWebRepository(WebConnection connection){
		super(connection);
	}
	
	public List<UnitOfMeasure> find(List<NameValuePair> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().Get(PATH, params);
		
		Type listType = new TypeToken<List<UnitOfMeasure>>() {}.getType();
		return new Gson().fromJson(json, listType);
	}
}
