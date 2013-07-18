package com.mss.infrastructure.ormlite;

import com.mss.domain.models.Product;

public class OrmliteProductRepository extends OrmliteGenericRepository<Product> {

	public OrmliteProductRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getProductDao());
	}
}
