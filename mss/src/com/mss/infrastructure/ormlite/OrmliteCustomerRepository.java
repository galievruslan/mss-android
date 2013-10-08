package com.mss.infrastructure.ormlite;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.mss.domain.models.Customer;

public class OrmliteCustomerRepository extends OrmliteGenericRepository<Customer> {

	public OrmliteCustomerRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getCustomerDao());
	}
	
	public Iterable<Customer> find(String searchCriteria) throws Exception {
		ArrayList<Customer> filtredCustomers = new ArrayList<Customer>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
		
		Iterable<Customer> customers = find();
		for (Customer customer : customers) {
			if (pattern.matcher(customer.getName()).find() || 
				pattern.matcher(customer.getAddress()).find())
				filtredCustomers.add(customer);
		}
		
		return filtredCustomers;
	}
}
