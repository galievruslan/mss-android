package com.mss.infrastructure.ormlite;

import com.mss.domain.models.RoutePointTemplate;

public class OrmliteRoutePointTemplateRepository extends OrmliteGenericRepository<RoutePointTemplate> {

	public OrmliteRoutePointTemplateRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRoutePointTemplateDao());
	}
}
