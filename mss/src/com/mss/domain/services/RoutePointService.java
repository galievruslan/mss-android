package com.mss.domain.services;

import java.util.Date;

import com.mss.domain.models.Preferences;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.models.Status;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmlitePreferencesRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;
import com.mss.infrastructure.ormlite.OrmliteStatusRepository;

public class RoutePointService {
	
	private DatabaseHelper databaseHelper;
	private OrmliteRoutePointRepository routePointRepo;
	private OrmlitePreferencesRepository preferencesRepo;
	private OrmliteStatusRepository statusRepo;
	
	private Preferences preferences;
	
	public RoutePointService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		routePointRepo = new OrmliteRoutePointRepository(this.databaseHelper);
		preferencesRepo = new OrmlitePreferencesRepository(this.databaseHelper);
		preferences = preferencesRepo.getById(Preferences.ID);
		statusRepo = new OrmliteStatusRepository(this.databaseHelper);
	}
	
	public RoutePoint getPointById(long id) throws Throwable {
		return routePointRepo.getById(id);
	}
	
	public Iterable<RoutePoint> getPointsByRoute(Route route) throws Throwable {
		return routePointRepo.findByRouteId(route.getId());
	}
	
	public RoutePoint cratePoint(Date date, ShippingAddress shippingAddress) throws Throwable{
		RouteService routeService = new RouteService(databaseHelper);
		Route route = routeService.getOnDate(date);
		// if not created yet
		if (route == null) {
			route = routeService.createRoute(date);
		}
		
		Status status = statusRepo.getById(preferences.getDefaultRoutePointStatusId());
		RoutePoint routePoint = new RoutePoint(route, shippingAddress, status);
		savePoint(routePoint);
		return routePoint;
	}
	
	public void savePoint(RoutePoint routePoint) throws Throwable {
		routePointRepo.save(routePoint);
	}
	
	public void deletePoint(RoutePoint routePoint) throws Throwable {
		routePointRepo.delete(routePoint);
	}
}
