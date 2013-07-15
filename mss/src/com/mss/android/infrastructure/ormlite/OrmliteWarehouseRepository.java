package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.Warehouse;

public class OrmliteWarehouseRepository extends OrmliteGenericRepository<Warehouse> {

	public OrmliteWarehouseRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getWarehouseDao());
	}
}
