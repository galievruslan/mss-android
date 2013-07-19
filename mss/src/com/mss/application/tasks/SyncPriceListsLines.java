package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastucture.web.dtos.translators.PriceListLineTranslator;

public class SyncPriceListsLines extends SyncTask<com.mss.infrastructure.web.dtos.PriceListLine, com.mss.domain.models.PriceListLine> {

	public SyncPriceListsLines(
			WebRepository<com.mss.infrastructure.web.dtos.PriceListLine> webRepo,
			IRepository<com.mss.domain.models.PriceListLine> modelRepo,
			PriceListLineTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncPriceListsLines(
			WebRepository<com.mss.infrastructure.web.dtos.PriceListLine> webRepo,
			IRepository<com.mss.domain.models.PriceListLine> modelRepo,
			PriceListLineTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
