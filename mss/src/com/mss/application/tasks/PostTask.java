package com.mss.application.tasks;

import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.data.IRepository;
import com.mss.infrastructure.web.WebServer;

import android.os.AsyncTask;

public abstract class PostTask<TModel extends IEntity> extends AsyncTask<Void, Void, Boolean> {
	
	WebServer webServer;
	IRepository<TModel> modelRepo;
	String url;
	private int attemptCount = 0;
	
	public PostTask(WebServer webServer, String url, IRepository<TModel> modelRepo){
		this.webServer = webServer;
		this.modelRepo = modelRepo;
		this.url = url;
	}	
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		Boolean result = false;
		while (attemptCount < 3) {
			try {
				result = doAttemptInBackground();
			} catch (Throwable e) {
				result = false;
			}
			if (result)
				break;
			
			attemptCount ++;
		}
		
		return result;
	}
		
	protected abstract Boolean doAttemptInBackground(Void... arg0) throws Throwable;

	@Override
    protected void onPostExecute(final Boolean success) {
        if (!success && attemptCount < 3) {
        	this.execute((Void)null);        	
        }
    }
}