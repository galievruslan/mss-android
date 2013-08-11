package com.mss.domain.services;

import com.mss.domain.models.Preferences;
import com.mss.domain.models.PriceList;
import com.mss.infrastructure.ormlite.DatabaseHelper;
import com.mss.infrastructure.ormlite.OrmlitePreferencesRepository;
import com.mss.infrastructure.ormlite.OrmlitePriceListRepository;

public class PriceListService {
	private DatabaseHelper databaseHelper;
	private OrmlitePriceListRepository priceListRepo;
	private OrmlitePreferencesRepository preferencesRepo;
	public PriceListService(DatabaseHelper databaseHelper) throws Throwable{
		this.databaseHelper = databaseHelper;
		priceListRepo = new OrmlitePriceListRepository(this.databaseHelper);
		preferencesRepo = new OrmlitePreferencesRepository(this.databaseHelper);
	}
	
	public PriceList getById(long id) throws Throwable {
		return priceListRepo.getById(id);
	}
	
	public PriceList getDefault() throws Throwable {
		Preferences preferences = preferencesRepo.getById(Preferences.ID);
		return priceListRepo.getById(preferences.getDefaultPriceListId());
	}
	
	public Iterable<PriceList> getPriceLists() throws Throwable{		 
		return priceListRepo.find();
	}
}
