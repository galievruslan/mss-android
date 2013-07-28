package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.PreferencesTranslator;

public class SyncPreferences extends SyncTask<com.mss.infrastructure.web.dtos.Preferences, com.mss.domain.models.Preferences> {

	public SyncPreferences(
			WebRepository<com.mss.infrastructure.web.dtos.Preferences> webRepo,
			IRepository<com.mss.domain.models.Preferences> modelRepo,
			PreferencesTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncPreferences(
			WebRepository<com.mss.infrastructure.web.dtos.Preferences> webRepo,
			IRepository<com.mss.domain.models.Preferences> modelRepo,
			PreferencesTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
