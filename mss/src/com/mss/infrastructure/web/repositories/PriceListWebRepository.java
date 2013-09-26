package com.mss.infrastructure.web.repositories;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.infrastructure.web.WebConnection;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.PriceList;

public class PriceListWebRepository extends WebRepository<PriceList> {
	final String PATH = "/synchronization/price_lists.json";
	public PriceListWebRepository(WebConnection connection){
		super(connection);
	}
	
	public List<PriceList> find(List<NameValuePair> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().get(PATH, params);
		
		Type listType = new TypeToken<List<PriceList>>() {}.getType();
		return new Gson().fromJson(json, listType);
	}
}
