package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Warehouse.TABLE_NAME)
public class Warehouse extends Entity {
	
	public Warehouse() {}
	
	public Warehouse(long id, String name, String address)  {
		super(id);
		this.name = name;
		this.address = address;
	}
	
	public Warehouse(long id, String name, String address, Boolean isDefault)  {
		this(id, name, address);
		this.isDefault = isDefault;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Warehouse.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Warehouse.ADDRESS_FIELD)
	private String address;
	
	public String getAddress(){
		return address;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.Warehouse.DEFAULT_FIELD)
	private boolean isDefault;
	
	public boolean getIsDefault(){
		return isDefault;
	}
	
	public void setIsDefault(boolean isDefault){
		this.isDefault = isDefault;
	}
}

