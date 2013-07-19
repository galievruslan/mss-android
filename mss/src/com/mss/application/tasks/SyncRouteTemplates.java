package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastucture.web.dtos.translators.RouteTemplateTranslator;

public class SyncRouteTemplates extends SyncTask<com.mss.infrastructure.web.dtos.RouteTemplate, com.mss.domain.models.RouteTemplate> {

	public SyncRouteTemplates(
			WebRepository<com.mss.infrastructure.web.dtos.RouteTemplate> webRepo,
			IRepository<com.mss.domain.models.RouteTemplate> modelRepo,
			RouteTemplateTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncRouteTemplates(
			WebRepository<com.mss.infrastructure.web.dtos.RouteTemplate> webRepo,
			IRepository<com.mss.domain.models.RouteTemplate> modelRepo,
			RouteTemplateTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
