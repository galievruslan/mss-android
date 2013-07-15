package com.mss.infrastructure.web.repositories;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.infrastructure.web.WebConnection;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.PriceListLine;

public class PriceListLineWebRepository extends WebRepository<PriceListLine> {
	final String PATH = "synchronization/product_prices.json";
	public PriceListLineWebRepository(WebConnection connection){
		super(connection);
	}
	
	public List<PriceListLine> find(List<NameValuePair> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().Get(PATH, params);
		
		Type listType = new TypeToken<List<PriceListLine>>() {}.getType();
		return new Gson().fromJson(json, listType);
	}
}
