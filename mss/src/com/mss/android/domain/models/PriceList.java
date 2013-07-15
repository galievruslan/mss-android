package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.PriceList.TABLE_NAME)
public class PriceList extends Entity {
	
	public PriceList() {}
	
	public PriceList(int id, String name)  {
		super(id);
		this.name = name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.PriceList.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
}
