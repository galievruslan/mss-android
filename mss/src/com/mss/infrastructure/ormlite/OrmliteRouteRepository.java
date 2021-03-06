package com.mss.infrastructure.ormlite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Constants;
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
	
	public Iterable<Route> findNotSynchronized() throws Throwable {
		String rawQuery = "select distinct " + 
				Constants.Tables.Route.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD  + " as [id], " +
				Constants.Tables.Route.TABLE_NAME + "." + Constants.Tables.Route.DATE_FIELD + " as [date] " +				
				"from " + Constants.Tables.Route.TABLE_NAME + " as " + Constants.Tables.Route.TABLE_NAME + " " +
				"inner join " + Constants.Tables.RoutePoint.TABLE_NAME + " as " + Constants.Tables.RoutePoint.TABLE_NAME + " on " +
				Constants.Tables.RoutePoint.TABLE_NAME + "." + Constants.Tables.RoutePoint.ROUTE_FIELD + " = " +
				Constants.Tables.Route.TABLE_NAME + "." + Constants.Tables.Entity.ID_FIELD + " AND " +
				Constants.Tables.RoutePoint.TABLE_NAME + "." + Constants.Tables.RoutePoint.SYNCHRONIZED_FIELD + " = 0";
		
		final DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		GenericRawResults<Route> rawResults =
				dao.queryRaw(rawQuery,
				    new RawRowMapper<Route>() {
				            public Route mapRow(String[] columnNames,
				              String[] resultColumns) {
				            	Date orderDate = null;
								try {
									orderDate = simpleDateFormat.parse(resultColumns[1]);
								} catch (ParseException e) {
									e.printStackTrace();
								}
				            	
				            	return new Route(Long.parseLong(resultColumns[0]), orderDate);
				        }
				    });
		
		Iterable<Route> routes = rawResults.getResults();
		rawResults.close();
		return routes;
	}
}
