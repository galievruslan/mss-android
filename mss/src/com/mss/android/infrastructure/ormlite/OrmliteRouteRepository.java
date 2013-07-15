package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.Route;

public class OrmliteRouteRepository extends OrmliteGenericRepository<Route> {

	public OrmliteRouteRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRouteDao());
	}
}
