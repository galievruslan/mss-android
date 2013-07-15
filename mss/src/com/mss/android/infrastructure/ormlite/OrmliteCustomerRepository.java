package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.Customer;

public class OrmliteCustomerRepository extends OrmliteGenericRepository<Customer> {

	public OrmliteCustomerRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getCustomerDao());
	}
}