package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.RouteTemplate;
import com.mss.domain.models.Week;

public class OrmliteRouteTemplateRepository extends OrmliteGenericRepository<RouteTemplate> {

	public OrmliteRouteTemplateRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getRouteTemplateDao());
	}
	
	public RouteTemplate findByDayOfWeek(Week.Days dayOfWeek) throws Exception {
		
		QueryBuilder<RouteTemplate, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.RouteTemplate.DAY_OF_WEEK_FIELD , dayOfWeek);
		return dao.queryForFirst(queryBuilder.prepare());
	}
}
