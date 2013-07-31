package com.mss.domain.services;

import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteStatusRepository;

public class StatusService {
	
	private DatabaseHelper databaseHelper;
	private OrmliteStatusRepository statusRepo;
	public StatusService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		statusRepo = new OrmliteStatusRepository(this.databaseHelper);
	}
	
}
