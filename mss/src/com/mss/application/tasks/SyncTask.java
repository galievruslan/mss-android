package com.mss.application.tasks;

import java.util.Date;

import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebRepository;
import com.mss.infrastructure.web.dtos.Dto;

import android.os.AsyncTask;

public abstract class SyncTask<TDto extends Dto, TModel extends IEntity> extends AsyncTask<Void, Void, Boolean> {

	protected WebRepository<TDto> webRepo;
	protected IRepository<TModel> modelRepo; 
	protected Date lastSync;
	
	int attemptCount = 0;
	
	public SyncTask(WebRepository<TDto> webRepo, IRepository<TModel> modelRepo){
		this.webRepo = webRepo;
		this.modelRepo = modelRepo;
	}
	
	public SyncTask(WebRepository<TDto> webRepo, IRepository<TModel> modelRepo, Date lastSync){
		this(webRepo, modelRepo);
		this.lastSync = lastSync;  
	}	
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		Boolean result = false;
		while (attemptCount < 3) {
			result = doAttemptInBackground();
			attemptCount ++;
		}
		
		return result;
	}
	
	protected abstract Boolean doAttemptInBackground(Void... arg0);

	@Override
    protected void onPostExecute(final Boolean success) {
        if (!success && attemptCount < 3) {
        	this.execute((Void)null);        	
        }
    }
}
