package com.mss.domain.services;

import java.util.ArrayList;

import android.util.Log;

import com.mss.domain.models.Preferences;
import com.mss.domain.models.PriceList;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmlitePreferencesRepository;
import com.mss.infrastructure.ormlite.OrmlitePriceListRepository;

public class PriceListService {
	private static final String TAG = PriceListService.class.getSimpleName();
	
	private DatabaseHelper databaseHelper;
	private OrmlitePriceListRepository priceListRepo;
	private OrmlitePreferencesRepository preferencesRepo;
	public PriceListService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		priceListRepo = new OrmlitePriceListRepository(this.databaseHelper);
		preferencesRepo = new OrmlitePreferencesRepository(this.databaseHelper);
	}
	
	public PriceList getById(long id) {
		try {
			return priceListRepo.getById(id);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null;
	}
	
	public PriceList getDefault() {
		Preferences preferences;
		try {
			preferences = preferencesRepo.getById(Preferences.ID);		
			return priceListRepo.getById(preferences.getDefaultPriceListId());
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return null;
	}
	
	public Iterable<PriceList> getPriceLists() {		 
		try {
			return priceListRepo.find();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());			
		}
		
		return new ArrayList<PriceList>();
	}
}
