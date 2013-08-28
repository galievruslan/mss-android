package com.mss.application;

import java.util.HashMap;

import com.mss.domain.models.OrderPickupItem;

public final class OrderEditContext {
	public static void Init(){
		if (pickedUpItems != null)
			pickedUpItems.clear();
		
		pickedUpItems = new HashMap<Long, OrderPickupItem>();
	}
	
	private static HashMap<Long, OrderPickupItem> pickedUpItems;
	
	public static HashMap<Long, OrderPickupItem> getPickedUpItems() {
		return pickedUpItems;
	}
}
