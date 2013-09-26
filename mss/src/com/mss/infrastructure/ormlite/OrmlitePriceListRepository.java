package com.mss.infrastructure.ormlite;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.mss.domain.models.PriceList;

public class OrmlitePriceListRepository extends OrmliteGenericRepository<PriceList> {

	public OrmlitePriceListRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getPriceListDao());
	}	
	
	public Iterable<PriceList> find(String searchCriteria) throws Throwable {
		ArrayList<PriceList> filtredPriceLists = new ArrayList<PriceList>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
		
		Iterable<PriceList> priceLists = find();
		for (PriceList priceList : priceLists) {
			if (pattern.matcher(priceList.getName()).find())
				filtredPriceLists.add(priceList);
		}
		
		return filtredPriceLists;
	}
}
