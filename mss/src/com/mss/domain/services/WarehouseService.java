package com.mss.domain.services;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import com.mss.domain.models.Warehouse;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteWarehouseRepository;

public class WarehouseService {
	private static final String TAG = WarehouseService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteWarehouseRepository warehouseRepo;
	public WarehouseService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		warehouseRepo = new OrmliteWarehouseRepository(this.databaseHelper);
	}
	
	public Warehouse getById(long id) throws Throwable {
		return warehouseRepo.getById(id);
	}
	
	public Warehouse getDefault() {	
		try {
			Iterator<Warehouse> warehouses = warehouseRepo.findDefault().iterator();
			if (warehouses.hasNext()) {
				return warehouses.next();
			} 
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null; 
	}
	
	public Iterable<Warehouse> getWarehouses(){	
		try {
			return warehouseRepo.find();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<Warehouse>();
	}
	
	public Iterable<Warehouse> getWarehouses(String searchCriteria){		
		try {
			return warehouseRepo.find(searchCriteria);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<Warehouse>();
	}
}
