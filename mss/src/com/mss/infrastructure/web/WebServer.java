package com.mss.infrastructure.web;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mss.infrastructure.web.dtos.ISO8601Utils;

public class WebServer {
	
	private String address;
	
	public WebServer(String address) {		
		this.address = address;
	}
	
	private CookieStore cookieStore;
	private HttpContext httpContext;
	
	public WebConnection connect(String login, String password) throws WebConnectionException, AuthenticationFailedException{		
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		webConnection = new WebConnection(this);			
		try {
			
			Get("/users/sign_in", new ArrayList<NameValuePair>());
						
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("authenticity_token", getCurrentConnection().getCsrf()));
			nameValuePairs.add(new BasicNameValuePair("user[username]", login));
			nameValuePairs.add(new BasicNameValuePair("user[password]", password));
			nameValuePairs.add(new BasicNameValuePair("user[remember_me]", "0"));
			nameValuePairs.add(new BasicNameValuePair("commit", "Sign in"));
			
			int status = Post("/users/sign_in", nameValuePairs).getStatusCode();
			System.out.print("Logon status: " + status + '\n');
			if (status != 302)
				throw new AuthenticationFailedException();
			
			return webConnection;
		} catch (JSONException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		throw new WebConnectionException();
	}
	
	public Date getTime() throws Exception{
        String json = Get("/synchronization/datetime.json", new ArrayList<NameValuePair>());
        
        
        JsonDeserializer<Date> jsonDateDeserializer = new JsonDeserializer<Date>() {
        	public Date deserialize(JsonElement json, Type typeOfT,
        		JsonDeserializationContext context) throws JsonParseException {
        		return ISO8601Utils.parse(json.getAsString());
			}
        };
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, jsonDateDeserializer).create();
        
		return gson.fromJson(json, ServerTime.class).getTime();
	} 
	
	public String Get(String url, List<NameValuePair> params) throws URISyntaxException, Exception {
		String getUrl = address + url;
		if (params.size() > 0) {
			getUrl += '?';
		}
		
		String getParamsEncoded = URLEncodedUtils.format(params, "utf-8");
		getUrl += getParamsEncoded;
		
		HttpGet httpGet = new HttpGet(getUrl);
		httpGet.addHeader("User-Agent", "MSS.Android mobile client");
						
		HttpResponse response = Dispatch(httpGet);
		String content = Parse(response);
		String csrfToken = extractCsrfToken(content);
		if (csrfToken != "") {
			getCurrentConnection().setCsrf(csrfToken);
			System.out.print("Get csrf token: " + csrfToken + '\n');
		}
				
		return content;
	}
	
	public PostResult Post(String url, List<NameValuePair> params) throws JSONException, Exception {
		
		HttpPost httpPost = new HttpPost(address + url);
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		httpPost.addHeader("User-Agent", "MSS.Android mobile client");
		
		HttpResponse response = Dispatch(httpPost);
		String content = Parse(response);
		String csrfToken = extractCsrfToken(content);
		if (csrfToken != "") {
			getCurrentConnection().setCsrf(csrfToken);
			System.out.print("Get csrf token: " + csrfToken + '\n');
		}
		
		return new PostResult(response.getStatusLine().getStatusCode(), content);
	}
	
	public PostResult Post(String url, JSONObject json) throws JSONException, Exception {
		
		HttpPost httpPost = new HttpPost(address + url);
		httpPost.setEntity(new StringEntity(json.toString()));
		httpPost.addHeader("User-Agent", "MSS.Android mobile client");
		
		HttpResponse response = Dispatch(httpPost);
		String content = Parse(response);
		String csrfToken = extractCsrfToken(content);
		if (csrfToken != "") {
			getCurrentConnection().setCsrf(csrfToken);
			System.out.print("Get csrf token: " + csrfToken + '\n');
		}
		
		return new PostResult(response.getStatusLine().getStatusCode(), content);
	}
	
	private HttpResponse Dispatch(HttpUriRequest request) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.FALSE);
		HttpResponse response = httpClient.execute(request, httpContext);
		
		return response;
	}
	
	private String Parse(HttpResponse response) throws IllegalStateException, IOException {
		InputStream stream = response.getEntity().getContent();
		BufferedReader getOutputBuffer = new BufferedReader(new InputStreamReader((stream)));
		
		String line;
		StringBuffer output = new StringBuffer("");
		while ((line = getOutputBuffer.readLine()) != null) {
			output.append(line);
		}
		
		return output.toString();
	}
	
	private String extractCsrfToken(String html){
		String csrfToken = "";
		
		try {
			final Pattern pattern = Pattern.compile("<input name=\"authenticity_token\" type=\"hidden\" value=\"(.+?)\" />");
			final Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				csrfToken = matcher.group(1); 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return csrfToken;
	}
	
	private WebConnection webConnection;
	public WebConnection getCurrentConnection(){
		return webConnection;
	}
	
	public class PostResult {
		public PostResult(int statusCode, String content) {
			this.statusCode = statusCode;
			this.content = content;
		}
		
		private int statusCode;
		
		public int getStatusCode(){
			return statusCode;
		}
		
		private String content;
		
		public String getContent(){
			return content;
		}
	}

	class ServerTime {
		private Date datetime;
		
		public Date getTime(){
			return datetime;
		}
	}
}
