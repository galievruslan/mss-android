package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePoint.TABLE_NAME)
public class RoutePoint extends Entity {
	
	public RoutePoint(Route route, ShippingAddress shippingAddress, Status status) {
		routeId = route.getId();
		shippingAddressId = shippingAddress.getId();
		shippingAddressName = shippingAddress.getName();
		shippingAddressValue = shippingAddress.getAddress();
		statusId = status.id;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.RoutePoint.ROUTE_FIELD)
	private int routeId;
	
	public int getRouteId(){
		return routeId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.RoutePoint.SHIPPING_ADDRESS_FIELD)
	private int shippingAddressId;
	
	public int getShippingAddressId(){
		return shippingAddressId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.RoutePoint.SHIPPING_ADDRESS_NAME_FIELD)
	private String shippingAddressName;
	
	public String getShippingAddressName(){
		return shippingAddressName;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.RoutePoint.SHIPPING_ADDRESS_VALUE_FIELD)
	private String shippingAddressValue;
	
	public String getShippingAddressValue(){
		return shippingAddressValue;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.RoutePoint.STATUS_FIELD)
	private int statusId;
	
	public int getStatusId(){
		return statusId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.RoutePoint.SYNCHRONIZED_FIELD)
	private boolean isSynchronized;
	
	public boolean getIsSynchronized(){
		return isSynchronized;
	}
}