package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Route.TABLE_NAME)
public class RouteTemplate extends Entity {
	
	public RouteTemplate(){}
	
	public RouteTemplate(int id, Week.Days dayOfWeek) {
		super(id);
		this.dayOfWeek = dayOfWeek;
	}
		
	@DatabaseField(canBeNull = false, dataType = DataType.ENUM_INTEGER, columnName = Constants.Tables.RouteTemplate.DAY_OF_WEEK_FIELD)
	private Week.Days dayOfWeek;
	
	public Week.Days getDayOfWeek(){
		return dayOfWeek;
	}
}