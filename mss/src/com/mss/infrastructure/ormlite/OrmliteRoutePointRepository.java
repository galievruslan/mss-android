package com.mss.infrastructure.ormlite;

import com.mss.domain.models.RoutePoint;

public class OrmliteRoutePointRepository extends OrmliteGenericRepository<RoutePoint> {

	public OrmliteRoutePointRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRoutePointDao());
	}
}
