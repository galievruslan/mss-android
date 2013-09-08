package com.mss.application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.mss.domain.models.OrderPickedUpItem;

public final class OrderEditContext {
	public static void Init(){
		if (pickedUpItems != null)
			pickedUpItems.clear();
		
		pickedUpItems = new HashMap<Long, OrderPickedUpItem>();
		
		if (selectedCategories != null)
			selectedCategories.clear();
		
		selectedCategories = new HashSet<Long>();
	}
	
	private static HashMap<Long, OrderPickedUpItem> pickedUpItems;
	
	public static HashMap<Long, OrderPickedUpItem> getPickedUpItems() {
		return pickedUpItems;
	}
	
	private static Set<Long> selectedCategories;
	
	public static Set<Long> getSelectedCategories() {
		return selectedCategories;
	}
	
	public static void setSelectedCategories(Set<Long> categories) {
		selectedCategories = categories;
	}
}
