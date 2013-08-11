package com.mss.domain.services;

import com.mss.domain.models.Order;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteOrderRepository;

public class OrderService {
	
	private DatabaseHelper databaseHelper;
	private OrmliteOrderRepository orderRepo;
	public OrderService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		orderRepo = new OrmliteOrderRepository(this.databaseHelper);
	}
	
	public Order getById(long id) throws Throwable {
		return orderRepo.getById(id);
	}
	
	public Iterable<Order> getByRoutePoint(RoutePoint routePoint) throws Throwable{		
		return orderRepo.findByRoutePointId(routePoint.getId());
	}
	
	public Order createOrder(RoutePoint routePoint) {
		Order order = new Order(routePoint);
		
		return order;
	}
	
	public void saveOrder(Order order) throws Throwable{
		orderRepo.save(order);
	}
	
	public void deleteOrder(Order order) throws Throwable {
		orderRepo.delete(order);
	}
}
