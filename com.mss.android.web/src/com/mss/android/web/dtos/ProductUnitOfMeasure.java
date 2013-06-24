package com.mss.android.web.dtos;

public class ProductUnitOfMeasure extends Dto {
	
	private int productId;
	
	public int getProductId(){
		return productId;
	}
		
	private int unitOfMeasureId;
	
	public int getUnitOfMeasureId(){
		return unitOfMeasureId;
	}
	
	private Boolean isBase;
	
	public Boolean getIsBase(){
		return isBase;
	}
	
	private int countInBase;
	
	public int getCountInBase(){
		return countInBase;
	}
}
