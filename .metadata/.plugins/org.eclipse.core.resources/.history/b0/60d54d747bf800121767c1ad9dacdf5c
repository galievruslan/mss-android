package com.mss.domain.services;

import com.mss.domain.models.Customer;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteCustomerRepository;

public class CustomerService {
	private DatabaseHelper databaseHelper;
	public CustomerService(DatabaseHelper databaseHelper){
		this.databaseHelper = databaseHelper;
	}
	
	public Iterable<Customer> Customers() throws Throwable{
		OrmliteCustomerRepository customerRepo = new OrmliteCustomerRepository(databaseHelper);
		return customerRepo.find();
	}
}
