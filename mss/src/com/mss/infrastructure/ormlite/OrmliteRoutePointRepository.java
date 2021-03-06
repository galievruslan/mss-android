package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.RoutePoint;

public class OrmliteRoutePointRepository extends OrmliteGenericRepository<RoutePoint> {

	public OrmliteRoutePointRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRoutePointDao());
	}
	
	public Iterable<RoutePoint> findByRouteId(long id) throws Throwable {
		
		QueryBuilder<RoutePoint, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.RoutePoint.ROUTE_FIELD , id);
		return dao.query(queryBuilder.prepare());
	}
	
	public RoutePoint getByRouteAndAddress(long routeId, long shippingAddressId) throws Throwable {
		
		QueryBuilder<RoutePoint, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder
			.where().eq(com.mss.domain.models.Constants.Tables.RoutePoint.ROUTE_FIELD, routeId)
			.and()
			.eq(com.mss.domain.models.Constants.Tables.RoutePoint.SHIPPING_ADDRESS_FIELD, shippingAddressId);
		return dao.queryForFirst(queryBuilder.prepare());
	}
}
