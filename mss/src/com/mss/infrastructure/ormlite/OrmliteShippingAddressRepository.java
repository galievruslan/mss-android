package com.mss.infrastructure.ormlite;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.ShippingAddress;

public class OrmliteShippingAddressRepository extends OrmliteGenericRepository<ShippingAddress> {

	public OrmliteShippingAddressRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getShippingAddressDao());
	}
	
	public Iterable<ShippingAddress> findByCustomerId(long id) throws Exception {
		
		QueryBuilder<ShippingAddress, Integer> queryBuilder = dao.queryBuilder();		
		queryBuilder.where().eq(com.mss.domain.models.Constants.Tables.ShippingAddress.CUSTOMER_FIELD ,id);
		return dao.query(queryBuilder.prepare());
	}
	
	public Iterable<ShippingAddress> findByCustomerId(long id, String searchCriteria) throws Throwable {
		ArrayList<ShippingAddress> filtredAdresses = new ArrayList<ShippingAddress>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
		
		Iterable<ShippingAddress> shippingAddresses = findByCustomerId(id);
		for (ShippingAddress shippingAddress : shippingAddresses) {
			if (pattern.matcher(shippingAddress.getName()).find() || 
				pattern.matcher(shippingAddress.getAddress()).find())
				filtredAdresses.add(shippingAddress);
		}
		
		return filtredAdresses;
	}
	
	public Iterable<ShippingAddress> find(String searchCriteria) throws Exception {
		
		ArrayList<ShippingAddress> filtredAdresses = new ArrayList<ShippingAddress>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
		
		Iterable<ShippingAddress> shippingAddresses = find();
		for (ShippingAddress shippingAddress : shippingAddresses) {
			if (pattern.matcher(shippingAddress.getName()).find() || 
				pattern.matcher(shippingAddress.getAddress()).find())
				filtredAdresses.add(shippingAddress);
		}
		
		return filtredAdresses;
	}
}
