package com.mss.domain.services;

import java.util.Date;
import java.util.Iterator;

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
	
	public Route getById(long id) throws Throwable {
		return routeRepo.getById(id);
	}
	
	public Route getOnDate(Date date) throws Throwable{
		Date dateOnly = new Date(date.getYear(), date.getMonth(), date.getDate());
		
		Iterator<Route> iterator = routeRepo.findByDate(dateOnly).iterator();
		if (iterator.hasNext())
			return iterator.next();
		
		return null;
	}
	
	public Route createRoute(Date date) {
		Date dateOnly = new Date(date.getYear(), date.getMonth(), date.getDate());		
		return new Route(dateOnly);
	}
	
	public void saveRoute(Route route) throws Throwable{
		routeRepo.save(route);
	}
}
