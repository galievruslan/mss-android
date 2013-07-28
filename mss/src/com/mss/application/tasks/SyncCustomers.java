package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.CustomerTranslator;

public class SyncCustomers extends SyncTask<com.mss.infrastructure.web.dtos.Customer, com.mss.domain.models.Customer> {

	public SyncCustomers(
			WebRepository<com.mss.infrastructure.web.dtos.Customer> webRepo,
			IRepository<com.mss.domain.models.Customer> modelRepo,
			CustomerTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncCustomers(
			WebRepository<com.mss.infrastructure.web.dtos.Customer> webRepo,
			IRepository<com.mss.domain.models.Customer> modelRepo,
			CustomerTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
