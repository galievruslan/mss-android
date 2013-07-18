package com.mss.infrastructure.ormlite;

import com.mss.domain.models.OrderItem;

public class OrmliteOrderItemRepository extends OrmliteGenericRepository<OrderItem> {

	public OrmliteOrderItemRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getOrderItemDao());
	}
}
