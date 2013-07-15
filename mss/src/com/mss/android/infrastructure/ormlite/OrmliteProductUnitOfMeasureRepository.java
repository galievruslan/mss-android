package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.ProductUnitOfMeasure;

public class OrmliteProductUnitOfMeasureRepository extends OrmliteGenericRepository<ProductUnitOfMeasure> {

	public OrmliteProductUnitOfMeasureRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getProductUnitOfMeasureDao());
	}
}