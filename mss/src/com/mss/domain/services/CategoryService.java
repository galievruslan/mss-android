package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Category;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmliteCategoryRepository;

public class CategoryService {
	private static final String TAG = CategoryService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmliteCategoryRepository categoryRepo;
	public CategoryService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		categoryRepo = new OrmliteCategoryRepository(this.databaseHelper);
	}	
	
	public Category getById(long id) {
		try {
			return categoryRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null;
	}
	
	public Iterable<Category> getCategories(){		 
		try {
			return categoryRepo.find();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<Category>();
	}
	
	public Iterable<Category> getCategoriesByIds(long[] ids){		 
		try {
			return categoryRepo.find(ids);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<Category>();
	}
}
