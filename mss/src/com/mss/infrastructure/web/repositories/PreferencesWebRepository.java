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
import com.mss.infrastructure.web.dtos.Preferences;

public class PreferencesWebRepository extends WebRepository<Preferences> {
	final String PATH = "/synchronization/settings.json";
	public PreferencesWebRepository(WebConnection connection){
		super(connection);
	}
	
	public List<Preferences> find(List<NameValuePair> params) throws URISyntaxException, Exception {
		String json = connection.getWebServer().get(PATH, params);
		
		Type type = new TypeToken<Preferences>() {}.getType();
		ArrayList<Preferences> preferences = new ArrayList<Preferences>();
		preferences.add((Preferences)new Gson().fromJson(json, type));
		return preferences;
	}
}
