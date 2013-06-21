package com.mss.android.domain.models;

import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Customer.TABLE_NAME)
public class Customer extends Entity {
	
	public Customer() {}
	
	public Customer(int id, String name)  {
		super(id);
		this.name = name;
		addresses = new ArrayList<ShippingAddress>();
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Customer.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@ForeignCollectionField
	private Collection<ShippingAddress> addresses;
	
	public void AddAddress(ShippingAddress address){
		addresses.add(address);		
	}
}
