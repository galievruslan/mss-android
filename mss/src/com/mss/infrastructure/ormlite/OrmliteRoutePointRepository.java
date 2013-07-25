package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Route;
import com.mss.domain.models.RoutePoint;

public class OrmliteRoutePointRepository extends OrmliteGenericRepository<RoutePoint> {

	public OrmliteRoutePointRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRoutePointDao());
	}
	
	public Iterable<RoutePoint> findByRoute(Route route) throws Throwable {
		
		QueryBuilder<RoutePoint, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().like(com.mss.domain.models.Constants.Tables.RoutePoint.ROUTE_FIELD , route.getId());
		return dao.query(queryBuilder.prepare());
	}
}
