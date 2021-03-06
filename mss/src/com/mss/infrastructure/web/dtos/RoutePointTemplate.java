package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class RoutePointTemplate extends Dto {
	
	@SerializedName("template_route_id")
	private long routeTemplateId;
	
	public long getRouteTemplateId(){
		return routeTemplateId;
	}
	
	@SerializedName("shipping_address_id")
	private long shippingAddressId;
	
	public long getShippingAddressId(){
		return shippingAddressId;
	}
}
