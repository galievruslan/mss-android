package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.RoutePointTemplate;

public class OrmliteRoutePointTemplateRepository extends OrmliteGenericRepository<RoutePointTemplate> {

	public OrmliteRoutePointTemplateRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getRoutePointTemplateDao());
	}
	
	public Iterable<RoutePointTemplate> findByRouteTemplateId(long id) throws Throwable {
		
		QueryBuilder<RoutePointTemplate, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.RoutePointTemplate.ROUTE_TEMPLATE_FIELD , id);
		return dao.query(queryBuilder.prepare());
	}
}
