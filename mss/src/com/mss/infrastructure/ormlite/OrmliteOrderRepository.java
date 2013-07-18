package com.mss.infrastructure.ormlite;

import com.mss.domain.models.Order;

public class OrmliteOrderRepository extends OrmliteGenericRepository<Order> {

	public OrmliteOrderRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getOrderDao());
	}
}
