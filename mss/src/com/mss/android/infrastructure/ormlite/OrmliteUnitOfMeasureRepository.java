package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.UnitOfMeasure;

public class OrmliteUnitOfMeasureRepository extends OrmliteGenericRepository<UnitOfMeasure> {

	public OrmliteUnitOfMeasureRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getUnitOfMeasureDao());
	}
}
