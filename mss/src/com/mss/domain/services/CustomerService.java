package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Customer;
import com.mss.domain.models.ShippingAddress;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteCustomerRepository;

public class CustomerService {
	private static final String TAG = CustomerService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteCustomerRepository customerRepo;
	public CustomerService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		customerRepo = new OrmliteCustomerRepository(this.databaseHelper);
	}
	
	public Customer getById(long id) {
		try {
			return customerRepo.getById(id);
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return null;
	}
	
	public Customer getByShippingAddress(ShippingAddress shippingAddress) {
		try {
			return customerRepo.getById(shippingAddress.getCustomerId());
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return null;
	}
	
	public Iterable<Customer> getCustomers() {
		try {
			return customerRepo.find();
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return new ArrayList<Customer>();
	}
	
	public Iterable<Customer> getCustomers(String searchCriteria) {
		try {
			return customerRepo.find(searchCriteria);
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return new ArrayList<Customer>();
	}
}
