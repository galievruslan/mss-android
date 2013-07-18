package com.mss.infrastructure.ormlite;

import com.mss.domain.models.ShippingAddress;

public class OrmliteShippingAddressRepository extends OrmliteGenericRepository<ShippingAddress> {

	public OrmliteShippingAddressRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getShippingAddressDao());
	}
}
