package com.mss.infrastructure.ormlite;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Warehouse;

public class OrmliteWarehouseRepository extends OrmliteGenericRepository<Warehouse> {

	public OrmliteWarehouseRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getWarehouseDao());
	}
	
	public Iterable<Warehouse> findDefault() throws Exception {
		
		QueryBuilder<Warehouse, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.Warehouse.DEFAULT_FIELD , true);
		return dao.query(queryBuilder.prepare());
	}
	
	public Iterable<Warehouse> find(String searchCriteria) throws Exception {
		ArrayList<Warehouse> filtredWarehouses = new ArrayList<Warehouse>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
		
		Iterable<Warehouse> warehouses = find();
		for (Warehouse warehouse : warehouses) {
			if (pattern.matcher(warehouse.getName()).find() || 
				pattern.matcher(warehouse.getAddress()).find())
				filtredWarehouses.add(warehouse);
		}
		
		return filtredWarehouses;
	}
}
