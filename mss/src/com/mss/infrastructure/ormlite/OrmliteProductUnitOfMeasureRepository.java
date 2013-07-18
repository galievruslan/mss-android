package com.mss.infrastructure.ormlite;

import com.mss.domain.models.ProductUnitOfMeasure;

public class OrmliteProductUnitOfMeasureRepository extends OrmliteGenericRepository<ProductUnitOfMeasure> {

	public OrmliteProductUnitOfMeasureRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getProductUnitOfMeasureDao());
	}
}
