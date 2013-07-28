package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.CategoryTranslator;

public class SyncCategories extends SyncTask<com.mss.infrastructure.web.dtos.Category, com.mss.domain.models.Category> {

	public SyncCategories(
			WebRepository<com.mss.infrastructure.web.dtos.Category> webRepo,
			IRepository<com.mss.domain.models.Category> modelRepo,
			CategoryTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncCategories(
			WebRepository<com.mss.infrastructure.web.dtos.Category> webRepo,
			IRepository<com.mss.domain.models.Category> modelRepo,
			CategoryTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
