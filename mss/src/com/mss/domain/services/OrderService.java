package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Order;
import com.mss.domain.models.OrderItem;
import com.mss.domain.models.OrderPickupItem;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteOrderItemRepository;
import com.mss.infrastructure.ormlite.OrmliteOrderPickupItemRepository;
import com.mss.infrastructure.ormlite.OrmliteOrderRepository;

public class OrderService {
	private static final String TAG = OrderService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteOrderRepository orderRepo;
	private OrmliteOrderItemRepository orderItemRepo;
	private OrmliteOrderPickupItemRepository orderPickUpItemRepo;
	public OrderService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		orderRepo = new OrmliteOrderRepository(this.databaseHelper);
		orderItemRepo = new OrmliteOrderItemRepository(this.databaseHelper);
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
	
	public void saveOrder(Order order, Iterable<OrderPickupItem> pickedUpItems){
		try {
			orderRepo.save(order);
			Iterable<OrderItem> items = orderItemRepo.findByOrderId(order.getId());
			for (OrderItem orderItem : items) {
				boolean found = false;				
				for (OrderPickupItem pickedUpItem : pickedUpItems) {
					if (orderItem.getProductId() == pickedUpItem.getProductId()) {
						orderItem.setUnitOfMeasureId(pickedUpItem.getUoMId());
						orderItem.setCountInUnitOfMeasure(pickedUpItem.getCountInBase());
						orderItem.setCount(pickedUpItem.getCount());
						orderItem.setPrice(pickedUpItem.getItemPrice());
						orderItemRepo.save(orderItem);						
						found = true;
						
						break;
					}
				}
				
				if (!found) 
					orderItemRepo.delete(orderItem);
			}	
			
			for (OrderPickupItem pickedUpItem : pickedUpItems) {				
				boolean found = false;
				for (OrderItem orderItem : items) {
					if (orderItem.getProductId() == pickedUpItem.getProductId()) {
						found = true;						
						break;
					}
				}
				
				if (!found) {
					OrderItem newOrderItem = new OrderItem(order.getId(), pickedUpItem.getProductId());
					newOrderItem.setUnitOfMeasureId(pickedUpItem.getUoMId());
					newOrderItem.setCountInUnitOfMeasure(pickedUpItem.getCountInBase());
					newOrderItem.setCount(pickedUpItem.getCount());
					newOrderItem.setPrice(pickedUpItem.getItemPrice());
					orderItemRepo.save(newOrderItem);		
				}
			}
			
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
