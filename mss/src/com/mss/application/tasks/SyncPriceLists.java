package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.translators.PriceListTranslator;

public class SyncPriceLists extends SyncTask<com.mss.infrastructure.web.dtos.PriceList, com.mss.domain.models.PriceList> {

	public SyncPriceLists(
			WebRepository<com.mss.infrastructure.web.dtos.PriceList> webRepo,
			IRepository<com.mss.domain.models.PriceList> modelRepo,
			PriceListTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncPriceLists(
			WebRepository<com.mss.infrastructure.web.dtos.PriceList> webRepo,
			IRepository<com.mss.domain.models.PriceList> modelRepo,
			PriceListTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
