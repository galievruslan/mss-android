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
		
		inOrderOnly = false;
		inStockOnly = false;
		modifyed = false;
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
	
	private static boolean inOrderOnly;
	
	public static boolean getInOrder() {
		return inOrderOnly;
	}
	
	public static void setInOrder(boolean showinOrderOnly) {
		inOrderOnly = showinOrderOnly;
	}
	
	private static boolean inStockOnly;
	
	public static boolean getInStock() {
		return inStockOnly;
	}
	
	public static void setInStock(boolean showinStockOnly) {
		inStockOnly = showinStockOnly;
	}
	
	private static boolean modifyed;
	
	public static boolean getIsModifyed(){
		return modifyed;
	}
	
	public static void setIsModifyed(boolean isModifyed){
		modifyed = isModifyed;
	}
}
