package com.mss.android.web.repositories;

import static org.junit.Assert.*;

import org.junit.Test;

public class WebServerTest {

	@Test
	public void test() {
		WebServer webServer = new WebServer("http://mss.alkotorg.com:3000");
		try {
			WebConnection connection = webServer.connect("manager", "423200");
		} catch (WebConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
