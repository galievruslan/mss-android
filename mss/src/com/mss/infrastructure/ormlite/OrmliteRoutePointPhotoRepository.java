package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.RoutePointPhoto;

public class OrmliteRoutePointPhotoRepository extends OrmliteGenericRepository<RoutePointPhoto> {

	public OrmliteRoutePointPhotoRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getRoutePointPhotoDao());
	}
	
	public Iterable<RoutePointPhoto> findByRoutePointId(long id) throws Exception {
		
		QueryBuilder<RoutePointPhoto, Integer> queryBuilder = dao.queryBuilder();		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.RoutePointPhoto.ROUTE_POINT_FIELD ,id);
		return dao.query(queryBuilder.prepare());
	}
}
