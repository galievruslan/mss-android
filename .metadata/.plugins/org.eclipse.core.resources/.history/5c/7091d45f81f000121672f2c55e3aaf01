package com.mss.application.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;

public class SyncProductsUnitsOfMeasures extends SyncTask<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure, com.mss.domain.models.ProductUnitOfMeasure> {

	public SyncProductsUnitsOfMeasures(
			WebRepository<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure> webRepo,
			IRepository<com.mss.domain.models.ProductUnitOfMeasure> modelRepo) {
		super(webRepo, modelRepo);
	}
	
	public SyncProductsUnitsOfMeasures(
			WebRepository<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure> webRepo,
			IRepository<com.mss.domain.models.ProductUnitOfMeasure> modelRepo, Date lastSync) {
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
			List<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure> dtos = webRepo.find(params);
			for (com.mss.infrastructure.web.dtos.ProductUnitOfMeasure dto : dtos) {
				if (!dto.getIsValid()) {
					com.mss.domain.models.ProductUnitOfMeasure invalidModel = modelRepo.getById(dto.getId());
					if (invalidModel != null)
						modelRepo.delete(invalidModel);
				}	
				
				modelRepo.save(new com.mss.domain.models.ProductUnitOfMeasure(dto.getId(), dto.getProductId(), dto.getUnitOfMeasureId(), dto.getIsBase(), dto.getCountInBase()));			
			}
		} catch (Throwable e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}

}
