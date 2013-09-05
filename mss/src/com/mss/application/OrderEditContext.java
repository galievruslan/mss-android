package com.mss.application;

import java.util.HashMap;

import com.mss.domain.models.OrderPickedUpItem;

public final class OrderEditContext {
	public static void Init(){
		if (pickedUpItems != null)
			pickedUpItems.clear();
		
		pickedUpItems = new HashMap<Long, OrderPickedUpItem>();
	}
	
	private static HashMap<Long, OrderPickedUpItem> pickedUpItems;
	
	public static HashMap<Long, OrderPickedUpItem> getPickedUpItems() {
		return pickedUpItems;
	}
}