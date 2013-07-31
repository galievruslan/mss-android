package com.mss.domain.services;

import com.mss.domain.models.Customer;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteCustomerRepository;

public class CustomerService {
	private DatabaseHelper databaseHelper;
	private OrmliteCustomerRepository customerRepo;
	public CustomerService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		customerRepo = new OrmliteCustomerRepository(this.databaseHelper);
	}
	
	public Customer getById(long id) throws Throwable {
		return customerRepo.getById(id);
	}
	
	public Iterable<Customer> getCustomers() throws Throwable{		 
		return customerRepo.find();
	}
}
