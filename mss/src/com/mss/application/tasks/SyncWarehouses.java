package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.WarehouseTranslator;

public class SyncWarehouses extends SyncTask<com.mss.infrastructure.web.dtos.Warehouse, com.mss.domain.models.Warehouse> {

	public SyncWarehouses(
			WebRepository<com.mss.infrastructure.web.dtos.Warehouse> webRepo,
			IRepository<com.mss.domain.models.Warehouse> modelRepo,
			WarehouseTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncWarehouses(
			WebRepository<com.mss.infrastructure.web.dtos.Warehouse> webRepo,
			IRepository<com.mss.domain.models.Warehouse> modelRepo,
			WarehouseTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
