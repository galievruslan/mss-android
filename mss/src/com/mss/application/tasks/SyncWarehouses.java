package com.mss.application.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;

public class SyncWarehouses extends SyncTask<com.mss.infrastructure.web.dtos.Warehouse, com.mss.domain.models.Warehouse> {

	public SyncWarehouses(
			WebRepository<com.mss.infrastructure.web.dtos.Warehouse> webRepo,
			IRepository<com.mss.domain.models.Warehouse> modelRepo) {
		super(webRepo, modelRepo);
	}
	
	public SyncWarehouses(
			WebRepository<com.mss.infrastructure.web.dtos.Warehouse> webRepo,
			IRepository<com.mss.domain.models.Warehouse> modelRepo, Date lastSync) {
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
			List<com.mss.infrastructure.web.dtos.Warehouse> dtos = webRepo.find(params);
			for (com.mss.infrastructure.web.dtos.Warehouse dto : dtos) {
				if (!dto.getIsValid()) {
					com.mss.domain.models.Warehouse invalidModel = modelRepo.getById(dto.getId());
					if (invalidModel != null)
						modelRepo.delete(invalidModel);
				}	
				
				modelRepo.save(new com.mss.domain.models.Warehouse(dto.getId(), dto.getName(), dto.getAddress(), dto.getIsDefault()));			
			}
		} catch (Throwable e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}

}
