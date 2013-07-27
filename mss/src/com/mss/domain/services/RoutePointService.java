package com.mss.domain.services;

import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;

public class RoutePointService {
	
	private DatabaseHelper databaseHelper;
	public RoutePointService(DatabaseHelper databaseHelper){
		this.databaseHelper = databaseHelper;
	}
	
	public RoutePoint getPointById(long id) throws Throwable {
		OrmliteRoutePointRepository routePointRepo = new OrmliteRoutePointRepository(databaseHelper);
		return routePointRepo.getById(id);
	}
	
	public Iterable<RoutePoint> getPointsByRoute(Route route) throws Throwable {
		OrmliteRoutePointRepository routePointRepo = new OrmliteRoutePointRepository(databaseHelper);
		return routePointRepo.findByRouteId(route.getId());
	}
	
	public void savePoint(RoutePoint routePoint) throws Throwable {
		OrmliteRoutePointRepository routePointRepo = new OrmliteRoutePointRepository(databaseHelper);
		routePointRepo.save(routePoint);
	}
	
	public void deletePoint(RoutePoint routePoint) throws Throwable {
		OrmliteRoutePointRepository routePointRepo = new OrmliteRoutePointRepository(databaseHelper);
		routePointRepo.delete(routePoint);
	}
}