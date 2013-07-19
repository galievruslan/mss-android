package com.mss.application.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.Dto;
import com.mss.infrastucture.web.dtos.translators.Translator;

import android.os.AsyncTask;

public abstract class SyncTask<TDto extends Dto, TModel extends IEntity> extends AsyncTask<Void, Void, Boolean> {

	protected WebRepository<TDto> webRepo;
	protected IRepository<TModel> modelRepo;
	protected Translator<TDto, TModel> translator;
	protected Integer pageSize;
	protected Date lastSync;
	
	int attemptCount = 0;
	
	public SyncTask(WebRepository<TDto> webRepo, IRepository<TModel> modelRepo, Translator<TDto, TModel> translator, Integer pageSize){
		this.webRepo = webRepo;
		this.modelRepo = modelRepo;
		this.translator = translator;
		this.pageSize = pageSize;
	}
	
	public SyncTask(WebRepository<TDto> webRepo, IRepository<TModel> modelRepo, Translator<TDto, TModel> translator, Integer pageSize, Date lastSync){
		this(webRepo, modelRepo, translator, pageSize);
		this.lastSync = lastSync;  
	}	
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		Boolean result = false;
		while (attemptCount < 3) {
			result = doAttemptInBackground();
			if (result)
				break;
			
			attemptCount ++;
		}
		
		return result;
	}
		
	protected Boolean doAttemptInBackground(Void... arg0) {
		Boolean result = true;
		Integer pageNo = 1;
		
		try {
			List<TDto> dtos;
			
			do {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("page", pageNo.toString()));
				params.add(new BasicNameValuePair("page_size", pageSize.toString()));
				if (lastSync != null) {
					params.add(new BasicNameValuePair("updated_at", lastSync.toString()));
				}
				
				dtos = webRepo.find(params);
				for (TDto dto : dtos) {
					if (!dto.getIsValid()) {
						TModel invalidModel = modelRepo.getById(dto.getId());
						if (invalidModel != null)
							modelRepo.delete(invalidModel);
					}					
				
					modelRepo.save(translator.Translate(dto));			
				}
				
				pageNo ++;
			}
			while (dtos.size() == pageSize);
		} catch (Throwable e) {
			result = false;
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
    protected void onPostExecute(final Boolean success) {
        if (!success && attemptCount < 3) {
        	this.execute((Void)null);        	
        }
    }
}
