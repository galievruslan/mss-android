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
}