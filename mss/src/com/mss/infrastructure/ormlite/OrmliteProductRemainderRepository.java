package com.mss.infrastructure.ormlite;

import com.mss.domain.models.ProductRemainder;

public class OrmliteProductRemainderRepository extends OrmliteGenericRepository<ProductRemainder> {

	public OrmliteProductRemainderRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getProductRemainderDao());
	}
}
