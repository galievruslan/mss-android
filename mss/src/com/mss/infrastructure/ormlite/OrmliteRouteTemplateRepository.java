package com.mss.infrastructure.ormlite;

import com.mss.domain.models.RouteTemplate;

public class OrmliteRouteTemplateRepository extends OrmliteGenericRepository<RouteTemplate> {

	public OrmliteRouteTemplateRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRouteTemplateDao());
	}
}
