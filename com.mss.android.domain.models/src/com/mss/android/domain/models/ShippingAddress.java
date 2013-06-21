package com.mss.android.domain.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "shipping_address")
public class ShippingAddress extends Entity {
	public ShippingAddress() {}
	
	public ShippingAddress(int id, String name, String address, Customer customer)  {
		super(id);
		this.name = name;
		this.address = address;
		this.customer = customer;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, width=255, columnName = "name")
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, width=255, columnName = "address")
	private String address;
	
	public String getAddress(){
		return address;
	}
	
	@DatabaseField(foreign=true, columnName="customer_id")
    private Customer customer;
}
