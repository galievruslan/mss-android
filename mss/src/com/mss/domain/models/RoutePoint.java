package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePoint.TABLE_NAME)
public class RoutePoint extends Entity {
	
	public RoutePoint() {}
	
	public RoutePoint(Route route, Customer customer, ShippingAddress shippingAddress, Status status) {
		routeId = route.getId();
		shippingAddressId = shippingAddress.getId();
		shippingAddressName = shippingAddress.getName();
		shippingAddressValue = shippingAddress.getAddress();
		statusId = status.getId();
		statusName = status.getName();
		debt = customer.getDebt();
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.RoutePoint.ROUTE_FIELD)
	private long routeId;
	
	public long getRouteId(){
		return routeId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.RoutePoint.SHIPPING_ADDRESS_FIELD)
	private long shippingAddressId;
	
	public long getShippingAddressId(){
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
	
	public void setShippingAddress(ShippingAddress shippingAddress){
		this.shippingAddressId = shippingAddress.getId();
		this.shippingAddressName = shippingAddress.getName();
		this.shippingAddressValue = shippingAddress.getAddress();
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.RoutePoint.STATUS_FIELD)
	private long statusId;
	
	public long getStatusId(){
		return statusId;
	}	
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.RoutePoint.STATUS_NAME_FIELD)
	private String statusName;
	
	public String getStatusName(){
		return statusName;
	}
	
	public void setStatus(Status status){
		this.statusId = status.getId();
		this.statusName = status.getName();
		this.isSynchronized = false;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.RoutePoint.SYNCHRONIZED_FIELD)
	private boolean isSynchronized;
	
	public boolean getIsSynchronized(){
		return isSynchronized;
	}
	
	public void setSynchronized(){
		this.isSynchronized = true;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.RoutePoint.DEBT_FIELD)
	private double debt;
	
	public double getDebt(){
		return debt;
	}
	
	public void setDebt(double debt){
		this.debt = debt;
	}
}
