package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.RoutePointTemplate;

public class OrmliteRoutePointTemplateRepository extends OrmliteGenericRepository<RoutePointTemplate> {

	public OrmliteRoutePointTemplateRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getRoutePointTemplateDao());
	}
}
