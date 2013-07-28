package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RouteTemplate.TABLE_NAME)
public class RouteTemplate extends Entity {
	
	public RouteTemplate(){}
	
	public RouteTemplate(long id, Week.Days dayOfWeek) {
		super(id);
		this.dayOfWeek = dayOfWeek;
	}
		
	@DatabaseField(canBeNull = false, dataType = DataType.ENUM_INTEGER, columnName = Constants.Tables.RouteTemplate.DAY_OF_WEEK_FIELD)
	private Week.Days dayOfWeek;
	
	public Week.Days getDayOfWeek(){
		return dayOfWeek;
	}
}