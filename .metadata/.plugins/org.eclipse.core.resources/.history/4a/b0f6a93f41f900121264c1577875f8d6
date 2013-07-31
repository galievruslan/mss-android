package com.mss.domain.services;

import com.mss.domain.models.ShippingAddress;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteShippingAddressRepository;

public class ShippingAddressService {
	private DatabaseHelper databaseHelper;
	public ShippingAddressService(DatabaseHelper databaseHelper){
		this.databaseHelper = databaseHelper;
	}
	
	public ShippingAddress getById(long id) throws Throwable{
		OrmliteShippingAddressRepository shippingAddressRepo = new OrmliteShippingAddressRepository(databaseHelper);
		return shippingAddressRepo.getById(id);
	}
}
