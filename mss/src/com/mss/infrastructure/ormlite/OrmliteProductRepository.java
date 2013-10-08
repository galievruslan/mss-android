package com.mss.infrastructure.ormlite;

import com.mss.domain.models.Product;

public class OrmliteProductRepository extends OrmliteGenericRepository<Product> {

	public OrmliteProductRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getProductDao());
	}
}
