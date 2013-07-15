package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.Order;

public class OrmliteOrderRepository extends OrmliteGenericRepository<Order> {

	public OrmliteOrderRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getOrderDao());
	}
}