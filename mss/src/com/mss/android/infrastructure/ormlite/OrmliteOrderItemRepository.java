package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.OrderItem;

public class OrmliteOrderItemRepository extends OrmliteGenericRepository<OrderItem> {

	public OrmliteOrderItemRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getOrderItemDao());
	}
}