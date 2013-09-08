package com.mss.application;

import java.util.HashSet;
import java.util.Set;

public final class CategorySelectContext {
	public static void Init(){
		if (selectedCategories != null)
			selectedCategories.clear();
		
		selectedCategories = new HashSet<Long>();
	}
	
	private static Set<Long> selectedCategories;
	
	public static Set<Long> getSelectedCategories() {
		return selectedCategories;
	}
}
