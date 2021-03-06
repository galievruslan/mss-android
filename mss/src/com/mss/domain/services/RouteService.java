package com.mss.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.util.Log;

import com.mss.domain.models.Customer;
import com.mss.domain.models.Preferences;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.RoutePointTemplate;
import com.mss.domain.models.RouteTemplate;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.models.Status;
import com.mss.domain.models.Week;
import com.mss.domain.models.Week.Days;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmlitePreferencesRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointPhotoRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointTemplateRepository;
import com.mss.infrastructure.ormlite.OrmliteRouteRepository;
import com.mss.infrastructure.ormlite.OrmliteRouteTemplateRepository;
import com.mss.infrastructure.ormlite.OrmliteShippingAddressRepository;
import com.mss.infrastructure.ormlite.OrmliteStatusRepository;

public class RouteService {
	private static final String TAG = RouteService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteRouteRepository routeRepo;
	private OrmliteRoutePointRepository routePointRepo;
	private OrmliteRoutePointPhotoRepository routePointPhotoRepo;
	private OrmliteRouteTemplateRepository routeTemplateRepo;
	private OrmliteRoutePointTemplateRepository routePointTemplateRepo;
	private OrmliteShippingAddressRepository shippingAddressRepo;
	private OrmliteStatusRepository statusRepo;
	private OrmlitePreferencesRepository preferencesRepo;	
	private CustomerService customerService;
	
	public RouteService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		routeRepo = new OrmliteRouteRepository(this.databaseHelper);
		routePointRepo = new OrmliteRoutePointRepository(this.databaseHelper);
		routePointPhotoRepo = new OrmliteRoutePointPhotoRepository(this.databaseHelper);
		routeTemplateRepo = new OrmliteRouteTemplateRepository(this.databaseHelper);
		routePointTemplateRepo = new OrmliteRoutePointTemplateRepository(this.databaseHelper);
		shippingAddressRepo = new OrmliteShippingAddressRepository(this.databaseHelper);
		statusRepo = new OrmliteStatusRepository(this.databaseHelper);
		customerService = new CustomerService(this.databaseHelper);
		preferencesRepo = new OrmlitePreferencesRepository(this.databaseHelper);		
	}
	
	public Route getById(long id) {
		try {
			return routeRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		return null;		
	}
	
	@SuppressWarnings("deprecation")
	public Route getOnDate(Date date) {
		try {
			Date dateOnly = new Date(date.getYear(), date.getMonth(), date.getDate());
		
			Iterator<Route> iterator = routeRepo.findByDate(dateOnly).iterator();
			if (iterator.hasNext())
				return iterator.next();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Route createRoute(Date date) {
		Date dateOnly = new Date(date.getYear(), date.getMonth(), date.getDate());		
		return new Route(dateOnly);
	}
	
	@SuppressWarnings("deprecation")
	public boolean isRouteTemplateOnDateExist(Date date) {
		try {
			int day = date.getDay();
			
			Week.Days dayOfWeek = null;
			switch (day){
			case 1: 
				dayOfWeek = Days.Monday;
				break;
			case 2:
				dayOfWeek = Days.Tuesday;
				break;
			case 3:
				dayOfWeek = Days.Wednesday;
				break;
			case 4:
				dayOfWeek = Days.Thursday;
				break;
			case 5:
				dayOfWeek = Days.Friday;
				break;
			case 6:
				dayOfWeek = Days.Saturday;
				break;
			case 0:
				dayOfWeek = Days.Sunday;
				break;
			}
			
			RouteTemplate routeTemplate = routeTemplateRepo.findByDayOfWeek(dayOfWeek);
			return routeTemplate != null;
		}
		catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public Route copyRouteFromTemplate(Date date) {
		try {
			int day = date.getDay();
			
			Week.Days dayOfWeek = null;
			switch (day){
			case 1: 
				dayOfWeek = Days.Monday;
				break;
			case 2:
				dayOfWeek = Days.Tuesday;
				break;
			case 3:
				dayOfWeek = Days.Wednesday;
				break;
			case 4:
				dayOfWeek = Days.Thursday;
				break;
			case 5:
				dayOfWeek = Days.Friday;
				break;
			case 6:
				dayOfWeek = Days.Saturday;
				break;
			case 0:
				dayOfWeek = Days.Sunday;
				break;
			}
			
			RouteTemplate routeTemplate = routeTemplateRepo.findByDayOfWeek(dayOfWeek);
			Iterable<RoutePointTemplate> routePointTemplates;
			if (routeTemplate != null) {
				routePointTemplates = routePointTemplateRepo.findByRouteTemplateId(routeTemplate.getId());
			} else {
				routePointTemplates = new ArrayList<RoutePointTemplate>();
			}
		
			Route route = getOnDate(date);
			if (route == null) {
				route = createRoute(date);
				saveRoute(route);
			}
		
			Preferences preferences = preferencesRepo.getById(Preferences.ID);
			Status status = statusRepo.getById(preferences.getDefaultRoutePointStatusId());
		
			Iterable<RoutePoint> routePoints = routePointRepo.findByRouteId(route.getId());
			for (RoutePointTemplate routePointTemplate : routePointTemplates) {
			
				boolean alreadyExist = false;
				for (RoutePoint routePoint : routePoints) {								
					if (routePoint.getShippingAddressId() == routePointTemplate.getShippingAddressId()) {
						alreadyExist = true;
						break;
					}
				}
			
				if (!alreadyExist) {
					ShippingAddress shippingAddress = shippingAddressRepo.getById(routePointTemplate.getShippingAddressId());
					Customer customer = customerService.getByShippingAddress(shippingAddress);
					if (shippingAddress != null) {
						RoutePoint routePoint = new RoutePoint(route, customer, shippingAddress, status);
						routePointRepo.save(routePoint);
					}
				}
			}
		
			return route;
		}
		catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		return null;
	}
	
	public boolean hasNotSynchronizedData(){
		try {
			boolean hasRoutePointsToSync = routeRepo.findNotSynchronized().iterator().hasNext();
			boolean hasRoutePointPhotosToSync = routePointPhotoRepo.findNotSynchronized().iterator().hasNext();
			
			return hasRoutePointsToSync || hasRoutePointPhotosToSync;
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		return false;
	}
	
	public void saveRoute(Route route) throws Throwable{
		routeRepo.save(route);
	}
}
