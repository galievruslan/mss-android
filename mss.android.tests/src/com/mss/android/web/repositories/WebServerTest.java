package com.mss.android.web.repositories;

import org.junit.Test;

public class WebServerTest {

	@Test
	public void testWebServerAuthentication() {
		WebServer webServer = new WebServer("http://mss.alkotorg.com:3000");
		try {
			webServer.connect("admin", "423200");
		} catch (WebConnectionException | AuthenticationFailedException e) {
			e.printStackTrace();
		}
	}

}
