package com.mss.android.domain.models;

import java.util.Date;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Route.TABLE_NAME)
public class Route extends Entity {
	
	public Route() {}
	
	@DatabaseField(id = true, columnName = Constants.Tables.Entity.ID_FIELD, generatedId = true)
	protected Override id;
	
	@DatabaseField(canBeNull = false, dataType = DataType.DATE, columnName = Constants.Tables.Route.DATE_FIELD)
	private Date date;
	
	public Date getDate(){
		return date;
	}
}
