package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Status.TABLE_NAME)
public class Status extends Entity {
	
	public Status() {}
	
	public Status(int id, String name)  {
		super(id);
		this.name = name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Status.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
}
