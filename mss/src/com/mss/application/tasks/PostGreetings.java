package com.mss.application.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.application.services.SystemService;
import com.mss.infrastructure.web.WebServer;

public class PostGreetings extends PostTask<com.mss.domain.models.Order> {

	public PostGreetings(WebServer webServer, String url) {
		super(webServer, url, null);
	}

	@Override
	protected Boolean doAttemptInBackground(Void... arg0) throws Throwable {	
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("client_type", "Android (" + SystemService.getSystemVersion() + ")"));
		nameValuePairs.add(new BasicNameValuePair("client_version",SystemService.getApplicationVersion()));
		webServer.Post(url, nameValuePairs);
		return true;
	}
}
