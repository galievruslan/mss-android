package com.mss.domain.services;

import java.util.Date;

import com.mss.domain.models.Route;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteRouteRepository;

public class RouteService {
	
	private DatabaseHelper databaseHelper;
	public RouteService(DatabaseHelper databaseHelper){
		this.databaseHelper = databaseHelper;
	}
	
	public Route getOnDate(Date date) throws Throwable{
		OrmliteRouteRepository routeRepo = new OrmliteRouteRepository(databaseHelper);
		return routeRepo.findByDate(date).iterator().next();
	}
}
