package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class RouteTemplate extends Dto {		
	@SerializedName("day_of_week")
	private int dayOfWeekNo;
	
	public int getDayOfWeekNo(){
		return dayOfWeekNo;
	}
}