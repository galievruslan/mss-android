package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastucture.web.dtos.translators.ShippingAddressTranslator;

public class SyncShippingAddresses extends SyncTask<com.mss.infrastructure.web.dtos.ShippingAddress, com.mss.domain.models.ShippingAddress> {

	public SyncShippingAddresses(
			WebRepository<com.mss.infrastructure.web.dtos.ShippingAddress> webRepo,
			IRepository<com.mss.domain.models.ShippingAddress> modelRepo,
			ShippingAddressTranslator translator,
			Integer pageSize) {
		super(webRepo, modelRepo, translator, pageSize);
	}
	
	public SyncShippingAddresses(
			WebRepository<com.mss.infrastructure.web.dtos.ShippingAddress> webRepo,
			IRepository<com.mss.domain.models.ShippingAddress> modelRepo,
			ShippingAddressTranslator translator,
			Integer pageSize, 
			Date lastSync) {
		super(webRepo, modelRepo, translator, pageSize, lastSync);
	}
}
