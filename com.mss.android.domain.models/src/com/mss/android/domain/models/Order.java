package com.mss.android.domain.models;

import java.math.BigDecimal;
import java.util.*;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Order.TABLE_NAME)
public class Order extends Entity {
	
	public Order() {
		items = new ArrayList<OrderItem>();
	}
	
	@DatabaseField(id = true, columnName = Constants.Tables.Entity.ID_FIELD, generatedId = true)
	protected Override id;
	
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
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.Order.SHIPPING_ADDRESS_FIELD)
	private ShippingAddress shippingAddress;
	
	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.Order.PRICE_LIST_FIELD)
	private PriceList priceList;
	
	public PriceList getPriceList() {
		return priceList;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.Order.WAREHOUSE_FIELD)
	private Warehouse warehouse;
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.Order.AMOUNT_FIELD)
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
	private Boolean isSynchronized;
	
	public Boolean getIsSynchronized(){
		return isSynchronized;
	}
	
	@ForeignCollectionField
	private Collection<OrderItem> items;
	
	public void AddItem(OrderItem item){
		items.add(item);		
	}
}