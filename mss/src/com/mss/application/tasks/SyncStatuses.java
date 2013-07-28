package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.StatusTranslator;

public class SyncStatuses extends SyncTask<com.mss.infrastructure.web.dtos.Status, com.mss.domain.models.Status> {

	public SyncStatuses(
			WebRepository<com.mss.infrastructure.web.dtos.Status> webRepo,
			IRepository<com.mss.domain.models.Status> modelRepo,
			StatusTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncStatuses(
			WebRepository<com.mss.infrastructure.web.dtos.Status> webRepo,
			IRepository<com.mss.domain.models.Status> modelRepo,
			StatusTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
