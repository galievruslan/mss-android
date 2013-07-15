package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.RouteTemplate;

public class OrmliteRouteTemplateRepository extends OrmliteGenericRepository<RouteTemplate> {

	public OrmliteRouteTemplateRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRouteTemplateDao());
	}
}