package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePointTemplate.TABLE_NAME)
public class RoutePointTemplate extends Entity {
	
	public RoutePointTemplate() {}
	
	public RoutePointTemplate(long id, long routeTemplateId, long shippingAddressId) {
		super(id);
		this.routeTemplateId = routeTemplateId;
		this.shippingAddressId = shippingAddressId;
	}
		
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.RoutePointTemplate.ROUTE_TEMPLATE_FIELD)
	private long routeTemplateId;
	
	public long getRouteTemplateId(){
		return routeTemplateId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.RoutePointTemplate.SHIPPING_ADDRESS_FIELD)
	private long shippingAddressId;
	
	public long getShippingAddressId(){
		return shippingAddressId;
	}
}
