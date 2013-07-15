package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.PriceListLine;

public class OrmlitePriceListLineRepository extends OrmliteGenericRepository<PriceListLine> {

	public OrmlitePriceListLineRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getPriceListLineDao());
	}
}