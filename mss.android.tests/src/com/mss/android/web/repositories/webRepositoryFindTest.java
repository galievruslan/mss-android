package com.mss.android.web.repositories;

import java.util.HashMap;

import org.junit.Test;

import com.mss.android.web.dtos.Customer;

public class webRepositoryFindTest {

	@Test
	public void findTest() throws Exception {
		WebServer webServer = new WebServer("http://mss.alkotorg.com:3000");
		WebConnection connection = webServer.connect("manager", "423200");
		GenericWebRepository<Customer> webRepo = new GenericWebRepository<Customer>(connection, "/synchronization/customers.json");
		Customer[] customers = webRepo.find(new HashMap<String, String>());
	}
}
