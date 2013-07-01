package com.mss.android.web.repositories;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.mss.android.web.dtos.Customer;

public class webRepositoryFindTest {

	@Test
	public void findTest() throws Exception {
		WebServer webServer = new WebServer("http://mss.alkotorg.com:3000");
		WebConnection connection = webServer.connect("manager", "423200");
		GenericWebRepository<Customer> webRepo = new CustomerWebRepository(connection, "/synchronization/customers.json");
		List<Customer> customers = webRepo.find(new HashMap<String, String>());
		
		for (Customer customer : customers) {
			System.out.println(customer.getId());
		}
	}
}
