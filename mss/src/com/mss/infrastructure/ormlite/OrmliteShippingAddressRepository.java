package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.ShippingAddress;

public class OrmliteShippingAddressRepository extends OrmliteGenericRepository<ShippingAddress> {

	public OrmliteShippingAddressRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getShippingAddressDao());
	}
	
	public Iterable<ShippingAddress> findByCustomerId(long id) throws Throwable {
		
		QueryBuilder<ShippingAddress, Integer> queryBuilder = dao.queryBuilder();		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.ShippingAddress.CUSTOMER_FIELD ,id);
		return dao.query(queryBuilder.prepare());
	}
}
