package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePointTemplate.TABLE_NAME)
public class RoutePointTemplate extends Entity {
	
	public RoutePointTemplate() {}
	
	public RoutePointTemplate(int id, RouteTemplate routeTemplate, ShippingAddress shippingAddress) {
		super(id);
		this.routeTemplate = routeTemplate;
		this.shippingAddress = shippingAddress;
	}
		
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.RoutePointTemplate.ROUTE_TEMPLATE_FIELD)
	private RouteTemplate routeTemplate;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.RoutePointTemplate.SHIPPING_ADDRESS_FIELD)
	private ShippingAddress shippingAddress;
	
	public ShippingAddress getShippingAddress(){
		return shippingAddress;
	}
}
