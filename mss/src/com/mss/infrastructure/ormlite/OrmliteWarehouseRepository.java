package com.mss.infrastructure.ormlite;

import com.mss.domain.models.Warehouse;

public class OrmliteWarehouseRepository extends OrmliteGenericRepository<Warehouse> {

	public OrmliteWarehouseRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getWarehouseDao());
	}
}
