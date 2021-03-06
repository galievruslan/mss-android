package com.mss.infrastructure.web.dtos;

import com.google.gson.annotations.SerializedName;

public class ProductRemainder extends Dto {
	
	public ProductRemainder() {
		setIsValid(true);
	}
	
	@SerializedName("product_id")
	private long productId;
	
	public long getProductId(){
		return productId;
	}
		
	@SerializedName("warehouse_id")
	private long warehouseId;
	
	public long getWarehouseId(){
		return warehouseId;
	}
	
	@SerializedName("unit_of_measure_id")
	private long unitOfMeasureId;
	
	public long getUnitOfMeasureId(){
		return unitOfMeasureId;
	}
		
	@SerializedName("quantity")
	private int quantity;
	
	public int getQuantity(){
		return quantity;
	}
}
