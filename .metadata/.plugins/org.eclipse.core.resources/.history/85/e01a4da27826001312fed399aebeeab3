package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Warehouse;

public class OrmliteWarehouseRepository extends OrmliteGenericRepository<Warehouse> {

	public OrmliteWarehouseRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getWarehouseDao());
	}
	
	public Iterable<Warehouse> findDefault() throws Throwable {
		
		QueryBuilder<Warehouse, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.Warehouse.DEFAULT_FIELD , true);
		return dao.query(queryBuilder.prepare());
	}
}
