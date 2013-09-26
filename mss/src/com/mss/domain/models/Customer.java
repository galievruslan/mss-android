package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Customer.TABLE_NAME)
public class Customer extends Entity {
	
	public Customer() {}
	
	public Customer(long id, String name, String address)  {
		super(id);
		this.name = name;
		this.address = address;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Customer.NAME_FIELD, columnDefinition = "COLLATE NOCASE")
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Customer.ADDRESS_FIELD, columnDefinition = "COLLATE NOCASE")
	private String address;
	
	public String getAddress(){
		return address;
	}
}
