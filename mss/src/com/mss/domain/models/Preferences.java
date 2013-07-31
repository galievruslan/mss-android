package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Preferences.TABLE_NAME)
public class Preferences extends Entity {
	public static final long ID = 1;
		
	public Preferences()  {
		super(ID);
	}

	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.Preferences.DEFAULT_STATUS_FIELD)
	private long defaultRoutePointStatusId;
	
    public long getDefaultRoutePointStatusId() {
    	return defaultRoutePointStatusId;
    }
    
    public void setDefaultRoutePointStatusId(long value) {
    	defaultRoutePointStatusId = value;
    }
    
    @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.Preferences.DEFAULT_ATTENDED_STATUS_FIELD)
    private long defaultRoutePointAttendedStatusId;
    
    public long getDefaultRoutePointAttendedStatusId() {
    	return defaultRoutePointAttendedStatusId;
    }
    
    public void setDefaultRoutePointAttendedStatusId(long value) {
    	defaultRoutePointAttendedStatusId = value;
    }
    
    @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.Preferences.DEFAULT_PRICE_LIST)
    private long defaultPriceListId;
    
    public long getDefaultPriceListId() {
    	return defaultPriceListId;
    }
    
    public void setDefaultPriceListId(long value) {
    	defaultPriceListId = value;
    }
}