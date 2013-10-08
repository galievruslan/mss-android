package com.mss.infrastructure.ormlite;

import com.mss.domain.models.UnitOfMeasure;

public class OrmliteUnitOfMeasureRepository extends OrmliteGenericRepository<UnitOfMeasure> {

	public OrmliteUnitOfMeasureRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getUnitOfMeasureDao());
	}
}
