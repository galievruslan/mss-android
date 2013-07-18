package com.mss.application.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;

public class SyncPriceListsLines extends SyncTask<com.mss.infrastructure.web.dtos.PriceListLine, com.mss.domain.models.PriceListLine> {

	public SyncPriceListsLines(
			WebRepository<com.mss.infrastructure.web.dtos.PriceListLine> webRepo,
			IRepository<com.mss.domain.models.PriceListLine> modelRepo) {
		super(webRepo, modelRepo);
	}
	
	public SyncPriceListsLines(
			WebRepository<com.mss.infrastructure.web.dtos.PriceListLine> webRepo,
			IRepository<com.mss.domain.models.PriceListLine> modelRepo, Date lastSync) {
		super(webRepo, modelRepo, lastSync);
	}

	@Override
	protected Boolean doAttemptInBackground(Void... arg0) {
		Boolean result = true;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (lastSync != null) {
			params.add(new BasicNameValuePair("updated_at", lastSync.toString()));
		}
		
		try {
			List<com.mss.infrastructure.web.dtos.PriceListLine> dtos = webRepo.find(params);
			for (com.mss.infrastructure.web.dtos.PriceListLine dto : dtos) {
				if (!dto.getIsValid()) {
					com.mss.domain.models.PriceListLine invalidModel = modelRepo.getById(dto.getId());
					if (invalidModel != null)
						modelRepo.delete(invalidModel);
				}	
				
				modelRepo.save(new com.mss.domain.models.PriceListLine(dto.getId(), dto.getPriceListId(), dto.getProductId(), dto.getPrice()));			
			}
		} catch (Throwable e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}

}
