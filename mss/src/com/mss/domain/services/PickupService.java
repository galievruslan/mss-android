package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.OrderPickupItem;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteOrderPickupItemRepository;
import com.mss.utils.IterableHelpers;

public class PickupService {
	private static final String TAG = PickupService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteOrderPickupItemRepository orderPickUpItemRepo;
	
	public PickupService(DatabaseHelper databaseHelper, long priceListId, long warehouseId) throws Throwable{
		this.databaseHelper = databaseHelper;
		orderPickUpItemRepo = new OrmliteOrderPickupItemRepository(this.databaseHelper, priceListId, warehouseId);
	}
	
	public OrderPickupItem getOrderPickupItemById(long id) {
		try {
			return orderPickUpItemRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}
	
	public Iterable<OrderPickupItem> getOrderPickupItems(Iterable<Long> categoryFilter) {
		try {
			return orderPickUpItemRepo.findByPriceListId(IterableHelpers.toArray(Long.class, categoryFilter));
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return new ArrayList<OrderPickupItem>();
		}
	}
	
	public Iterable<OrderPickupItem> getOrderPickupItems(Iterable<Long> categoryFilter, String searchCriteria) {
		try {
			return orderPickUpItemRepo.findByPriceListId(IterableHelpers.toArray(Long.class, categoryFilter), searchCriteria);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			return new ArrayList<OrderPickupItem>();
		}
	}
}
