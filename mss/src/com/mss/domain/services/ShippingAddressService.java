package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Customer;
import com.mss.domain.models.ShippingAddress;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteShippingAddressRepository;

public class ShippingAddressService {
	private static final String TAG = ShippingAddressService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	OrmliteShippingAddressRepository shippingAddressRepo;
	public ShippingAddressService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		shippingAddressRepo = new OrmliteShippingAddressRepository(this.databaseHelper);
	}
	
	public ShippingAddress getById(long id) {
		try {
			return shippingAddressRepo.getById(id);
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return null;
		
	}
	
	public Iterable<ShippingAddress> findByCustomer(Customer customer) {
		try {
			return shippingAddressRepo.findByCustomerId(customer.getId());
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return new ArrayList<ShippingAddress>();
	}
	
	public Iterable<ShippingAddress> findByCustomer(Customer customer, String searchCriteria) {
		try {
			return shippingAddressRepo.findByCustomerId(customer.getId(), searchCriteria);
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return new ArrayList<ShippingAddress>();
	}
	
	public Iterable<ShippingAddress> find() {	
		try {
			return shippingAddressRepo.find();
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return new ArrayList<ShippingAddress>();
	}
	
	public Iterable<ShippingAddress> find(String searchCriteria) {	
		try {
			return shippingAddressRepo.find(searchCriteria);
		} catch (Throwable throwable) {
			Log.e(TAG, throwable.getMessage());			
		}
		
		return new ArrayList<ShippingAddress>();
	}
}
