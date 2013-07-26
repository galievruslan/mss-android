package com.mss.domain.services;

import java.util.Date;

import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;
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
	
	public RoutePoint getPointById(int id) throws Throwable {
		OrmliteRoutePointRepository routePointRepo = new OrmliteRoutePointRepository(databaseHelper);
		return routePointRepo.getById(id);
	}
	
	public void deletePoint(RoutePoint routePoint) throws Throwable {
		OrmliteRoutePointRepository routePointRepo = new OrmliteRoutePointRepository(databaseHelper);
		routePointRepo.delete(routePoint);
	}
}
