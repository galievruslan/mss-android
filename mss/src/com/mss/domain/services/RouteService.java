package com.mss.domain.services;

import java.util.Date;

import com.mss.domain.models.Route;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteRouteRepository;

public class RouteService {
	
	private DatabaseHelper databaseHelper;
	private OrmliteRouteRepository routeRepo;
	public RouteService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		routeRepo = new OrmliteRouteRepository(this.databaseHelper);
	}
	
	public Route getOnDate(Date date) throws Throwable{
		return routeRepo.findByDate(date).iterator().next();
	}
	
	public Route createRoute(Date date) {
		return new Route(date);
	}
}
