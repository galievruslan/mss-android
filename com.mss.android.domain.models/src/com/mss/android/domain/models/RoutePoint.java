package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePoint.TABLE_NAME)
public class RoutePoint extends Entity {
	
	public RoutePoint() {}
	
	@DatabaseField(id = true, columnName = Constants.Tables.Entity.ID_FIELD, generatedId = true)
	protected Override id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.RoutePoint.ROUTE_FIELD)
	private Route route;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.RoutePoint.SHIPPING_ADDRESS_FIELD)
	private ShippingAddress shippingAddress;
	
	public ShippingAddress getShippingAddress(){
		return shippingAddress;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.RoutePoint.STATUS_FIELD)
	private Status status;
	
	public Status getStatus(){
		return status;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.RoutePoint.SYNCHRONIZED_FIELD)
	private Boolean isSynchronized;
	
	public Boolean getIsSynchronized(){
		return isSynchronized;
	}
}
