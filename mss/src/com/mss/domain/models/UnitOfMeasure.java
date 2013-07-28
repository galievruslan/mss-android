package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.UnitOfMeasure.TABLE_NAME)
public class UnitOfMeasure extends Entity {
	
	public UnitOfMeasure() {}
	
	public UnitOfMeasure(long id, String name)  {
		super(id);
		this.name = name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.UnitOfMeasure.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
}
