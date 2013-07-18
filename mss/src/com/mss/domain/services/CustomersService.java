package com.mss.domain.services;

import com.mss.domain.models.Customer;
import com.mss.infrastructure.data.IRepository;

public class CustomersService {
	private IRepository<Customer> customersRepo;
	public CustomersService(IRepository<Customer> customersRepo) {
		this.customersRepo = customersRepo;
	}
}
