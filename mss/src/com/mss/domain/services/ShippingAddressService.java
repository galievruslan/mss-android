package com.mss.domain.services;

import com.mss.domain.models.Customer;
import com.mss.domain.models.ShippingAddress;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteShippingAddressRepository;

public class ShippingAddressService {
	private DatabaseHelper databaseHelper;
	OrmliteShippingAddressRepository shippingAddressRepo;
	public ShippingAddressService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		shippingAddressRepo = new OrmliteShippingAddressRepository(this.databaseHelper);
	}
	
	public ShippingAddress getById(long id) throws Throwable{		
		return shippingAddressRepo.getById(id);
	}
	
	public Iterable<ShippingAddress> findByCustomer(Customer customer) throws Throwable{		 
		return shippingAddressRepo.findByCustomerId(customer.getId());
	}
	
	public Iterable<ShippingAddress> find() throws Throwable{		 
		return shippingAddressRepo.find();
	}
}
