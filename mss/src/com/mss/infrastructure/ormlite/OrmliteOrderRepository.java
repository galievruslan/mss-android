package com.mss.infrastructure.ormlite;

import java.util.Date;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Order;

public class OrmliteOrderRepository extends OrmliteGenericRepository<Order> {

	public OrmliteOrderRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getOrderDao());
	}
	
	public Iterable<Order> findByRoutePointId(long id) throws Throwable {
		
		QueryBuilder<Order, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.Order.ROUTE_POINT_FIELD , id);
		return dao.query(queryBuilder.prepare());
	}
	
	public boolean existForRoutePointId(long id) throws Throwable {
		
		QueryBuilder<Order, Integer> queryBuilder = dao.queryBuilder();
		
		return queryBuilder
			.where().eq(com.mss.domain.models.Constants.Tables.Order.ROUTE_POINT_FIELD , id)
			.countOf() > 0;
	}
	
	public Iterable<Order> findByDate(Date date) throws Throwable {
		
		QueryBuilder<Order, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.Order.ORDER_DATE_FIELD , date);
		return dao.query(queryBuilder.prepare());
	}
	
	public Iterable<Order> findNotSynchronized() throws Throwable {
		
		QueryBuilder<Order, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.where()
			.eq(com.mss.domain.models.Constants.Tables.Order.SYNCHRONIZED_FIELD , false)
			.and()
			.gt(com.mss.domain.models.Constants.Tables.Order.AMOUNT_FIELD , 0);
		return dao.query(queryBuilder.prepare());
	}
}
