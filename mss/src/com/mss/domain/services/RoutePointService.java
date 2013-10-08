package com.mss.domain.services;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

import com.mss.domain.models.Customer;
import com.mss.domain.models.Preferences;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.domain.models.ShippingAddress;
import com.mss.domain.models.Status;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteOrderRepository;
import com.mss.infrastructure.ormlite.OrmlitePreferencesRepository;
import com.mss.infrastructure.ormlite.OrmliteRoutePointRepository;
import com.mss.infrastructure.ormlite.OrmliteStatusRepository;

public class RoutePointService {
	private static final String TAG = RoutePointService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteRoutePointRepository routePointRepo;
	private OrmlitePreferencesRepository preferencesRepo;
	private OrmliteStatusRepository statusRepo;
	private OrmliteOrderRepository orderRepo;
	private CustomerService customerService;
		
	public RoutePointService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		routePointRepo = new OrmliteRoutePointRepository(this.databaseHelper);
		preferencesRepo = new OrmlitePreferencesRepository(this.databaseHelper);		
		statusRepo = new OrmliteStatusRepository(this.databaseHelper);
		orderRepo = new OrmliteOrderRepository(this.databaseHelper);
		customerService = new CustomerService(this.databaseHelper);
	}
	
	public RoutePoint getById(long id) {
		try {
			return routePointRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null;
	}
	
	public Iterable<RoutePoint> getPointsByRoute(Route route) {
		try {
			return routePointRepo.findByRouteId(route.getId());
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<RoutePoint>();
	}
	
	public RoutePoint getPointByDateAndAddress(Date date, ShippingAddress shippingAddress) {
		try {
			RouteService routeService = new RouteService(databaseHelper);
			Route route = routeService.getOnDate(date);
			
			if (route != null)
				return routePointRepo.getByRouteAndAddress(route.getId(), shippingAddress.getId());
			
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null;
	}
	
	public RoutePoint cratePoint(Date date, ShippingAddress shippingAddress){
		try {
			RouteService routeService = new RouteService(databaseHelper);
			Route route = routeService.getOnDate(date);
			// if not created yet
			if (route == null) {
				route = routeService.createRoute(date);
				routeService.saveRoute(route);
			}
		
			Preferences preferences = preferencesRepo.getById(Preferences.ID);
			Status status = statusRepo.getById(preferences.getDefaultRoutePointStatusId());
			Customer customer = customerService.getByShippingAddress(shippingAddress);
			
			RoutePoint routePoint = new RoutePoint(route, customer, shippingAddress, status);
			savePoint(routePoint);
			return routePoint;
			} catch (Throwable e) {
				Log.e(TAG, e.getMessage());			
			}
		
		return null;
	}
	
	public void changePointStatus(RoutePoint routePoint, Status status){
		routePoint.setStatus(status);
		savePoint(routePoint);
	}
	
	public void savePoint(RoutePoint routePoint) {
		try {
			routePointRepo.save(routePoint);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
	}
	
	public void deletePoint(RoutePoint routePoint) {
		try {
			routePointRepo.delete(routePoint);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
	}
	
	public boolean canBeEditedOrDeleted(long routePointId) {
		try {
			return !orderRepo.existForRoutePointId(routePointId);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		
		return false;
	}
}
