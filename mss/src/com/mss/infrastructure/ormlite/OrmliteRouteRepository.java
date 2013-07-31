package com.mss.infrastructure.ormlite;

import java.util.Date;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Route;

public class OrmliteRouteRepository extends OrmliteGenericRepository<Route> {

	public OrmliteRouteRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRouteDao());
	}
	
	public Iterable<Route> findByDate(Date date) throws Throwable {
		
		QueryBuilder<Route, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.Route.DATE_FIELD , date);
		return dao.query(queryBuilder.prepare());
	}
}
