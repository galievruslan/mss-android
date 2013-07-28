package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.UnitOfMeasureTranslator;

public class SyncUnitsOfMeasures extends SyncTask<com.mss.infrastructure.web.dtos.UnitOfMeasure, com.mss.domain.models.UnitOfMeasure> {

	public SyncUnitsOfMeasures(
			WebRepository<com.mss.infrastructure.web.dtos.UnitOfMeasure> webRepo,
			IRepository<com.mss.domain.models.UnitOfMeasure> modelRepo,
			UnitOfMeasureTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncUnitsOfMeasures(
			WebRepository<com.mss.infrastructure.web.dtos.UnitOfMeasure> webRepo,
			IRepository<com.mss.domain.models.UnitOfMeasure> modelRepo,
			UnitOfMeasureTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
