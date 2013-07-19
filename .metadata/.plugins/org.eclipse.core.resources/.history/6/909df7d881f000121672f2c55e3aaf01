package com.mss.application.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.domain.models.Week;
import com.mss.domain.models.Week.Days;
import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;

public class SyncRouteTemplates extends SyncTask<com.mss.infrastructure.web.dtos.RouteTemplate, com.mss.domain.models.RouteTemplate> {

	public SyncRouteTemplates(
			WebRepository<com.mss.infrastructure.web.dtos.RouteTemplate> webRepo,
			IRepository<com.mss.domain.models.RouteTemplate> modelRepo) {
		super(webRepo, modelRepo);
	}
	
	public SyncRouteTemplates(
			WebRepository<com.mss.infrastructure.web.dtos.RouteTemplate> webRepo,
			IRepository<com.mss.domain.models.RouteTemplate> modelRepo, Date lastSync) {
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
			List<com.mss.infrastructure.web.dtos.RouteTemplate> dtos = webRepo.find(params);
			for (com.mss.infrastructure.web.dtos.RouteTemplate dto : dtos) {
				if (!dto.getIsValid()) {
					com.mss.domain.models.RouteTemplate invalidModel = modelRepo.getById(dto.getId());
					if (invalidModel != null)
						modelRepo.delete(invalidModel);
				}	
				
				Week.Days dayOfWeek = Days.Monday;
				switch (dto.getDayOfWeekNo()){
				case 1: 
					dayOfWeek = Days.Monday;
					break;
				case 2:
					dayOfWeek = Days.Tuesday;
					break;
				case 3:
					dayOfWeek = Days.Wednesday;
					break;
				case 4:
					dayOfWeek = Days.Thursday;
					break;
				case 5:
					dayOfWeek = Days.Friday;
					break;
				case 6:
					dayOfWeek = Days.Saturday;
					break;
				case 7:
					dayOfWeek = Days.Sunday;
					break;
				}
				
				modelRepo.save(new com.mss.domain.models.RouteTemplate(dto.getId(), dayOfWeek));			
			}
		} catch (Throwable e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}

}
