package com.mss.domain.models;

import java.math.BigDecimal;
import java.util.*;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Order.TABLE_NAME)
public class Order extends Entity {
	
	public Order() {}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_TIME, columnName = Constants.Tables.Order.ORDER_DATE_FIELD)
	private Date orderDate;
	
	public Date getOrderDate(){
		return orderDate;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_TIME, columnName = Constants.Tables.Order.SHIPPING_DATE_FIELD)
	private Date shippingDate;
	
	public Date getShippingDate(){
		return shippingDate;
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = Constants.Tables.Order.SHIPPING_ADDRESS_FIELD)
	private int shippingAddressId;
	
	public int getShippingAddressId() {
		return shippingAddressId;
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = Constants.Tables.Order.PRICE_LIST_FIELD)
	private int priceListId;
	
	public int getPriceListId() {
		return priceListId;
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = Constants.Tables.Order.WAREHOUSE_FIELD)
	private int warehouseId;
	
	public int getWarehouseId() {
		return warehouseId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BIG_DECIMAL, columnName = Constants.Tables.Order.AMOUNT_FIELD)
	private BigDecimal amount;
	
	public BigDecimal getAmount(){
		return amount;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, width = 255, columnName = Constants.Tables.Order.NOTE_FIELD)
	private String note;
	
	public String getNote(){
		return note;
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
}