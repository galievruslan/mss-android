package com.mss.domain.models;

import java.util.Date;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Route.TABLE_NAME)
public class Route extends Entity {
	public Route() {				
	}
	
	public Route(Long id, Date date) {
		this.id = id;
		this.date = date;				
	}
	
	public Route(Date date) {
		this.date = date;				
	}
		
	@DatabaseField(canBeNull = false, dataType = DataType.DATE, columnName = Constants.Tables.Route.DATE_FIELD)
	private Date date;
	
	public Date getDate(){
		return date;
	}
}
