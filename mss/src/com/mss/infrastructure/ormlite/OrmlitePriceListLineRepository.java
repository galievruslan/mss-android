package com.mss.infrastructure.ormlite;

import com.mss.domain.models.PriceListLine;

public class OrmlitePriceListLineRepository extends OrmliteGenericRepository<PriceListLine> {

	public OrmlitePriceListLineRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getPriceListLineDao());
	}
}
