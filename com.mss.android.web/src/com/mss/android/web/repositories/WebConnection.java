package com.mss.android.web.repositories;

public class WebConnection {
	
	public WebConnection(WebServer webServer){
		this.webServer = webServer;
		this.cookie = "";
	}
	
	private WebServer webServer;
	
	public WebServer getWebServer(){
		return webServer;
	}
	
	private String cookie;
	public String getCookie(){
		return cookie;
	}
	
	public void setCookie(String cookie){
		this.cookie = cookie;
	}
	
	private String csrf;
	public String getCsrf(){
		return csrf;
	}
	
	public void setCsrf(String csrf){
		this.csrf = csrf;
	}
}
