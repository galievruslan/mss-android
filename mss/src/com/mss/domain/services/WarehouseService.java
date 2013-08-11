package com.mss.domain.services;

import java.util.Iterator;

import com.mss.domain.models.Warehouse;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteWarehouseRepository;

public class WarehouseService {
	private DatabaseHelper databaseHelper;
	private OrmliteWarehouseRepository warehouseRepo;
	public WarehouseService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		warehouseRepo = new OrmliteWarehouseRepository(this.databaseHelper);
	}
	
	public Warehouse getById(long id) throws Throwable {
		return warehouseRepo.getById(id);
	}
	
	public Warehouse getDefault() throws Throwable {
		Warehouse defaultWarehouse = null;
		
		Iterator<Warehouse> warehouses = warehouseRepo.findDefault().iterator();
		if (warehouses.hasNext()) {
			defaultWarehouse = warehouses.next();
		} 
		
		return defaultWarehouse; 
	}
	
	public Iterable<Warehouse> getCustomers() throws Throwable{		 
		return warehouseRepo.find();
	}
}
