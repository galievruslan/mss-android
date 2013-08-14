package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Order;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteOrderPickupItemRepository;
import com.mss.infrastructure.ormlite.OrmliteOrderRepository;

public class OrderService {
	private static final String TAG = OrderService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteOrderRepository orderRepo;
	private OrmliteOrderPickupItemRepository orderPickUpItemRepo;
	public OrderService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		orderRepo = new OrmliteOrderRepository(this.databaseHelper);
		orderPickUpItemRepo = new OrmliteOrderPickupItemRepository(this.databaseHelper);
	}
	
	public Order getById(long id) {
		try {
			return orderRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}
	
	public Iterable<Order> getByRoutePoint(RoutePoint routePoint){		
		try {
			return orderRepo.findByRoutePointId(routePoint.getId());
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return new ArrayList<Order>();
		}
	}
	
	public OrderPickupItem getOrderPickupItemById(long id) {
		try {
			return orderPickUpItemRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}
	
	public Iterable<OrderPickupItem> getOrderPickupItems(long priceListId) {
		try {
			return orderPickUpItemRepo.findByPriceListId(priceListId);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return new ArrayList<OrderPickupItem>();
		}
	}
	
	public Order createOrder(Route route, RoutePoint routePoint) {
		Order order = new Order(route, routePoint);		
		return order;
	}
	
	public void saveOrder(Order order){
		try {
			orderRepo.save(order);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public void deleteOrder(Order order) {
		try {
			orderRepo.delete(order);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	}
}
