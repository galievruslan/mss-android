package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastucture.web.dtos.translators.ProductTranslator;

public class SyncProducts extends SyncTask<com.mss.infrastructure.web.dtos.Product, com.mss.domain.models.Product> {

	public SyncProducts(
			WebRepository<com.mss.infrastructure.web.dtos.Product> webRepo,
			IRepository<com.mss.domain.models.Product> modelRepo,
			ProductTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncProducts(
			WebRepository<com.mss.infrastructure.web.dtos.Product> webRepo,
			IRepository<com.mss.domain.models.Product> modelRepo,
			ProductTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
