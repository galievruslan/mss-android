package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.Product;

public class OrmliteProductRepository extends OrmliteGenericRepository<Product> {

	public OrmliteProductRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getProductDao());
	}
}
