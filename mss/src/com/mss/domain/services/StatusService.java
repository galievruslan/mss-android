package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Status;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteStatusRepository;

public class StatusService {
	private static final String TAG = StatusService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteStatusRepository statusRepo;
	public StatusService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		statusRepo = new OrmliteStatusRepository(this.databaseHelper);
	}	
	
	public Status getById(long id) {
		try {
			return statusRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null;
	}
	
	public Iterable<Status> getStatuses(){		 
		try {
			return statusRepo.find();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<Status>();
	}
}
