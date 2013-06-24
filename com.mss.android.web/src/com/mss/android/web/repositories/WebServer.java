package com.mss.android.web.repositories;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class WebServer {
	
	String address;
	
	public WebServer(String address) {		
		this.address = address;
	}
	
	String login;
	String password;
	
	public WebConnection connect(String login, String password) throws WebConnectionException{		
		this.login = login;
		this.password = password;
		
		webConnection = new WebConnection(this);			
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpParams params = new BasicHttpParams();
			params.setParameter("http.protocol.handle-redirects",false);
						
			HttpGet httpGet = new HttpGet(address + "/users/sign_in");
			httpGet.addHeader("User-Agent", "MSS.Android mobile client");
			httpGet.setParams(params);
			
			String cookie = webConnection.getCookie();
			if (cookie.length() > 0) {
				httpGet.setHeader("Cookie", cookie);	
			}	
			
			HttpResponse response = httpClient.execute(httpGet);
						
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		 	
			String line;
			StringBuffer output = new StringBuffer("");
			while ((line = br.readLine()) != null) {
				output.append(line);
			}
			
			System.out.print(output);
			webConnection.setCsrf(extractCsrfToken(output.toString()));
			System.out.print("\n");
			System.out.print(webConnection.getCsrf());
			
			httpGet.releaseConnection();
			
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		
		return webConnection;
	}
	
	private String extractCsrfToken(String html){
		final Pattern pattern = Pattern.compile("<meta content=\"(.+?)\" name=\"csrf-token\" />");
		final Matcher matcher = pattern.matcher(html);
		matcher.find();
		return matcher.group(1);
	}
	
	private WebConnection webConnection;
	public WebConnection getCurrent(){
		return webConnection;
	}
}
