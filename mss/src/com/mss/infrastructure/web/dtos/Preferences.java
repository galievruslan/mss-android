package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class Preferences extends Dto {
	public Preferences(){
		setIsValid(true);
	}	
	
	@SerializedName("default_route_point_status_id")
    private long defaultRoutePointStatusId;
    public long getDefaultRoutePointStatusId() {
    	return defaultRoutePointStatusId;
    }
    
    @SerializedName("default_route_point_attended_status_id")
    private long defaultRoutePointAttendedStatusId;
    public long getDefaultRoutePointAttendedStatusId() {
    	return defaultRoutePointAttendedStatusId;
    }
    
    @SerializedName("default_price_list_id")
    private long defaultPriceListId;
    public long getDefaultPriceListId() {
    	return defaultPriceListId;
    }
    
    @SerializedName("photo_width_res")
    private int photoWidthResolution;
    public int getPhotoWidthResolution() {
    	return photoWidthResolution;
    }
    
    @SerializedName("photo_height_res")
    private int photoHeightResolution;
    public int getPhotoHeightResolution() {
    	return photoHeightResolution;
    }
    
    @SerializedName("message_pull_frequency")
    private int messagePoolFrequency;
    public int getMessagePoolFrequency() {
    	return messagePoolFrequency;
    }
}
