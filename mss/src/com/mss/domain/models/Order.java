package com.mss.domain.models;

import java.util.*;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Order.TABLE_NAME)
public class Order extends Entity {
	
	public Order() {}
	
	public Order(Route route, RoutePoint routePoint) {
		orderDate = route.getDate();
		routePointId = routePoint.getId();		
		amount = 0;
		note = "";
		uid = UUID.randomUUID();
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.Order.ROUTE_POINT_FIELD)
	private long routePointId;
	
	public long getRoutePointId(){
		return this.routePointId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DATE, columnName = Constants.Tables.Order.ORDER_DATE_FIELD)
	private Date orderDate;
	
	public Date getOrderDate(){
		return orderDate;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DATE, columnName = Constants.Tables.Order.SHIPPING_DATE_FIELD)
	private Date shippingDate;
	
	public Date getShippingDate(){
		return shippingDate;
	}
	
	public void setShippingDate(Date date) {
		shippingDate = date;
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.LONG, columnName = Constants.Tables.Order.CUSTOMER_FIELD)
	private long customerId;
	
	public long getCustomerId() {
		return customerId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Order.CUSTOMER_NAME_FIELD)
	private String customerName;
	
	public String getCustomerName(){
		return customerName;
	}
	
	public void setCustomer(Customer customer) {
		customerId = customer.getId();
		customerName = customer.getName();
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.LONG, columnName = Constants.Tables.Order.SHIPPING_ADDRESS_FIELD)
	private long shippingAddressId;
	
	public long getShippingAddressId() {
		return shippingAddressId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Order.SHIPPING_ADDRESS_NAME_FIELD)
	private String shippingAddressName;
	
	public String getShippingAddressName(){
		return shippingAddressName;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Order.SHIPPING_ADDRESS_VALUE_FIELD)
	private String shippingAddressValue;
	
	public String getShippingAddressValue(){
		return shippingAddressValue;
	}
	
	public void setShippingAddress(ShippingAddress shippingAddress) {
		shippingAddressId = shippingAddress.getId();
		shippingAddressName = shippingAddress.getName();
		shippingAddressValue = shippingAddress.getAddress();
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.LONG, columnName = Constants.Tables.Order.PRICE_LIST_FIELD)
	private long priceListId;
	
	public long getPriceListId() {
		return priceListId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Order.PRICE_LIST_NAME_FIELD)
	private String priceListName;
	
	public String getPriceListName(){
		return priceListName;
	}
	
	public void setPriceList(PriceList priceList) {
		priceListId = priceList.getId();
		priceListName = priceList.getName();
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.LONG, columnName = Constants.Tables.Order.WAREHOUSE_FIELD)
	private long warehouseId;
	
	public long getWarehouseId() {
		return warehouseId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Order.WAREHOUSE_NAME_FIELD)
	private String warehouseName;
	
	public String getWarehouseName(){
		return warehouseName;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		warehouseId = warehouse.getId();
		warehouseName = warehouse.getName();
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.Order.AMOUNT_FIELD)
	private double amount;
	
	public double getAmount(){
		return amount;
	}
	
	public void setAmount(double amount){
		this.amount = amount;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, width = 255, columnName = Constants.Tables.Order.NOTE_FIELD)
	private String note;
	
	public String getNote(){
		return note;
	}
	
	public void setNote(String note){
		this.note = note;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.UUID, columnName = Constants.Tables.Order.UID_FIELD)
	private UUID uid;
	
	public UUID getUid(){
		return uid;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.Order.SYNCHRONIZED_FIELD)
	private boolean isSynchronized;
	
	public boolean getIsSynchronized(){
		return isSynchronized;
	}
	
	public void setIsSynchronized(boolean isSynchronized){
		this.isSynchronized = isSynchronized;
	}
}