package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastucture.web.dtos.translators.ProductUnitOfMeasureTranslator;

public class SyncProductsUnitsOfMeasures extends SyncTask<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure, com.mss.domain.models.ProductUnitOfMeasure> {

	public SyncProductsUnitsOfMeasures(
			WebRepository<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure> webRepo,
			IRepository<com.mss.domain.models.ProductUnitOfMeasure> modelRepo,
			ProductUnitOfMeasureTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncProductsUnitsOfMeasures(
			WebRepository<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure> webRepo,
			IRepository<com.mss.domain.models.ProductUnitOfMeasure> modelRepo,
			ProductUnitOfMeasureTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
