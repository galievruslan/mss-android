package com.mss.infrastructure.web.repositories;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mss.infrastructure.web.WebConnection;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.Manager;

public class ManagerWebRepository extends WebRepository<Manager> {
	final String PATH = "/synchronization/manager.json";
	public ManagerWebRepository(WebConnection connection){
		super(connection);
	}
	
	public List<Manager> find(List<NameValuePair> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().get(PATH, params);
				
		Type type = new TypeToken<Manager>() {}.getType();
		ArrayList<Manager> managers = new ArrayList<Manager>();
		managers.add((Manager)new Gson().fromJson(json, type));
		return managers;
	}
}
