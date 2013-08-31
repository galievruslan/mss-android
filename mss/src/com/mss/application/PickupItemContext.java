package com.mss.application;

import com.mss.domain.models.OrderPickedUpItem;

public final class PickupItemContext {
	public static void Init(){
		if (pickedUpItem != null)
			pickedUpItem = null;
	}
	
	private static OrderPickedUpItem pickedUpItem;
	
	public static OrderPickedUpItem getPickedUpItem() {
		return pickedUpItem;
	}
	
	public static void setPickedUpItem(OrderPickedUpItem pickedUpItem){
		PickupItemContext.pickedUpItem = pickedUpItem;
	}
}
