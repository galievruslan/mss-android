package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.RoutePoint;

public class OrmliteRoutePointRepository extends OrmliteGenericRepository<RoutePoint> {

	public OrmliteRoutePointRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRoutePointDao());
	}
	
	public Iterable<RoutePoint> findByRouteId(long id) throws Throwable {
		
		QueryBuilder<RoutePoint, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().like(com.mss.domain.models.Constants.Tables.RoutePoint.ROUTE_FIELD , id);
		return dao.query(queryBuilder.prepare());
	}
}
