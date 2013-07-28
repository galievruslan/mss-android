package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastucture.web.dtos.translators.RoutePointTemplateTranslator;

public class SyncRoutePointsTemplates extends SyncTask<com.mss.infrastructure.web.dtos.RoutePointTemplate, com.mss.domain.models.RoutePointTemplate> {

	public SyncRoutePointsTemplates(
			WebRepository<com.mss.infrastructure.web.dtos.RoutePointTemplate> webRepo,
			IRepository<com.mss.domain.models.RoutePointTemplate> modelRepo,
			RoutePointTemplateTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncRoutePointsTemplates(
			WebRepository<com.mss.infrastructure.web.dtos.RoutePointTemplate> webRepo,
			IRepository<com.mss.domain.models.RoutePointTemplate> modelRepo,
			RoutePointTemplateTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
