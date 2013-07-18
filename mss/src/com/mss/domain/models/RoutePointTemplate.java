package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePointTemplate.TABLE_NAME)
public class RoutePointTemplate extends Entity {
	
	public RoutePointTemplate() {}
	
	public RoutePointTemplate(int id, int routeTemplateId, int shippingAddressId) {
		super(id);
		this.routeTemplateId = routeTemplateId;
		this.shippingAddressId = shippingAddressId;
	}
		
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.RoutePointTemplate.ROUTE_TEMPLATE_FIELD)
	private int routeTemplateId;
	
	public int getRouteTemplateId(){
		return routeTemplateId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.RoutePointTemplate.SHIPPING_ADDRESS_FIELD)
	private int shippingAddressId;
	
	public int getShippingAddressId(){
		return shippingAddressId;
	}
}
