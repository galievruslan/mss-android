package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class ProductUnitOfMeasure extends Dto {
	
	@SerializedName("product_id")
	private long productId;
	
	public long getProductId(){
		return productId;
	}
		
	@SerializedName("unit_of_measure_id")
	private long unitOfMeasureId;
	
	public long getUnitOfMeasureId(){
		return unitOfMeasureId;
	}
	
	private Boolean base;
	
	public Boolean getIsBase(){
		return base;
	}
	
	@SerializedName("count_in_base_unit")
	private int countInBaseUnit;
	
	public int getCountInBase(){
		return countInBaseUnit;
	}
}
