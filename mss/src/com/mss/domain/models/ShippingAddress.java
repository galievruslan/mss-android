package com.mss.domain.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Constants.Tables.ShippingAddress.TABLE_NAME)
public class ShippingAddress extends Entity {
	public ShippingAddress() {}
	
	public ShippingAddress(long id, String name, String address, long customerId)  {
		super(id);
		this.name = name;
		this.address = address;
		this.customerId = customerId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, width=255, columnName = Constants.Tables.ShippingAddress.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, width=255, columnName = Constants.Tables.ShippingAddress.ADDRESS_FIELD)
	private String address;
	
	public String getAddress(){
		return address;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.ShippingAddress.CUSTOMER_FIELD)
    private long customerId;
	
	public long getCustomerId(){
		return customerId;
	}
}
