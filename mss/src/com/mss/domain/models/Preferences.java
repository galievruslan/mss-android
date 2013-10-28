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
    
    @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.Preferences.DEFAULT_PRICE_LIST_FIELD)
    private long defaultPriceListId;
    
    public long getDefaultPriceListId() {
    	return defaultPriceListId;
    }
    
    public void setDefaultPriceListId(long value) {
    	defaultPriceListId = value;
    }
    
    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.Preferences.PHOTO_WIDTH_RESOLUTION_FIELD)
    private int photoWidthResolution;
    
    public int getPhotoWidthResolution() {
    	return photoWidthResolution;
    }
    
    public void setPhotoWidthResolution(int value) {
    	photoWidthResolution = value;
    }
    
    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.Preferences.PHOTO_HEIGHT_RESOLUTION_FIELD)
    private int photoHeightResolution;
    
    public int getPhotoHeightResolution() {
    	return photoHeightResolution;
    }
    
    public void setPhotoHeightResolution(int value) {
    	photoHeightResolution = value;
    }
    
    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.Preferences.MESSAGE_POOL_FREQUENCY_FIELD)
    private int messagePoolFrequency;
    
    public int getMessagePoolFrequency() {
    	return messagePoolFrequency;
    }
    
    public void setMessagePoolFrequency(int value) {
    	messagePoolFrequency = value;
    }
}
