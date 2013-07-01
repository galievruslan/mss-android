package com.mss.android.web.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServer {
	
	String address;
	
	public WebServer(String address) {		
		this.address = address;
	}
	
	String login;
	String password;
	
	CookieStore cookieStore;
	HttpContext httpContext;
	
	public WebConnection connect(String login, String password) throws WebConnectionException, AuthenticationFailedException{		
		this.login = login;
		this.password = password;
		
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		webConnection = new WebConnection(this);			
		try {
			
			Get("/users/sign_in", new HashMap<String, String>());
			
			//JSONObject jsonCredentials = new JSONObject();
			//jsonCredentials.put("username", login);
			//jsonCredentials.put("password", password);
			//JSONObject jsonUser = new JSONObject();
			//jsonUser.put("user", jsonCredentials);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("authenticity_token", getCurrentConnection().getCsrf()));
			nameValuePairs.add(new BasicNameValuePair("user[username]", login));
			nameValuePairs.add(new BasicNameValuePair("user[password]", password));
			nameValuePairs.add(new BasicNameValuePair("user[remember_me]", "0"));
			nameValuePairs.add(new BasicNameValuePair("commit", "Sign in"));
			
			int status = Post("/users/sign_in", nameValuePairs);
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
	
	public String Get(String url, Map<String, String> params) throws URISyntaxException, Exception {
		URIBuilder builder = new URIBuilder(address);
		builder.setPath(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.setParameter(entry.getKey(), entry.getValue());
		}
		URI uri = builder.build();
					
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader("User-Agent", "MSS.Android mobile client");
				
		//String cookie = getCurrentConnection().getCookie();		
		//if (cookie.length() > 0) {
		//	System.out.print("Set cookies: " + cookie + '\n');
		//	httpGet.setHeader("Cookie", cookie);	
		//}	
		
		HttpResponse response = Dispatch(httpGet);
		String content = Parse(response);
		String csrfToken = extractCsrfToken(content);
		if (csrfToken != "") {
			getCurrentConnection().setCsrf(csrfToken);
			System.out.print("Get csrf token: " + csrfToken + '\n');
		}
		
		httpGet.releaseConnection();		
		return content;
	}
	
	public int Post(String url, List<NameValuePair> params) throws JSONException, Exception {
		//if (getCurrentConnection().getCsrf() != "") {
		//	params.add(new BasicNameValuePair("authenticity_token", getCurrentConnection().getCsrf()));
			//data.put("authenticity_token", getCurrentConnection().getCsrf());
			//System.out.print("Set csrf token: " + getCurrentConnection().getCsrf() + '\n');
		//}
		
		//StringEntity entity = new StringEntity(data.toString());
		//entity.setContentType("application/json");
		//entity.setContentEncoding("utf-8");
		
		//params.add(new BasicNameValuePair("authenticity_token", getCurrentConnection().getCsrf()));
		HttpPost httpPost = new HttpPost(address + url);
		//httpPost.addHeader("Content-Type","application/json");
		httpPost.setEntity(new UrlEncodedFormEntity(params));
		//httpPost.setEntity(entity);
		httpPost.addHeader("User-Agent", "MSS.Android mobile client");
		//httpPost.addHeader("Accept", "application/json");
		//httpPost.addHeader("Accept-Encoding", "gzip, deflate, compress");
		//httpPost.addHeader("X-CSRF-Token", getCurrentConnection().getCsrf());
		
		//String cookie = webConnection.getCookie();		
		//if (cookie.length() > 0) {
		//	System.out.print("Set cookies: " + cookie + '\n');
		//	httpPost.setHeader("Cookie", cookie);	
		//}	
		
		HttpResponse response = Dispatch(httpPost);
		String content = Parse(response);
		String csrfToken = extractCsrfToken(content);
		if (csrfToken != "") {
			getCurrentConnection().setCsrf(csrfToken);
			System.out.print("Get csrf token: " + csrfToken + '\n');
		}
		
		httpPost.releaseConnection();		
		
		return response.getStatusLine().getStatusCode();
	}
	
	private HttpResponse Dispatch(HttpUriRequest request) throws IOException {
		//String nameAndPassword = login+":"+password;
		//String authorizationString = "Basic " + Base64.encodeBase64String(StringUtils.getBytesUtf8(nameAndPassword));
		
		HttpClient httpClient = new DefaultHttpClient();
		//request.addHeader("Authorization", authorizationString);
		//httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
	    //        CookiePolicy.BROWSER_COMPATIBILITY);
				
		HttpResponse response = httpClient.execute(request, httpContext);
		// set cookies
		//if (response.getFirstHeader("Set-Cookie") != null) {
		//	webConnection.setCookie(response.getFirstHeader("Set-Cookie").toString());
		//	System.out.print("Get cookies: " + webConnection.getCookie() + '\n');
		//}
		
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
}
