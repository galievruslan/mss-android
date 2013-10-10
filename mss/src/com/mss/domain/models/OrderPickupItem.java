package com.mss.domain.models;

import com.mss.infrastructure.data.IEntity;
import com.mss.utils.MathHelpers;

public class OrderPickupItem implements IEntity {
	
	public OrderPickupItem(long id, long productId, String productName, double price, long productUomId, long uomId, String uomName, int countInBase, int remainder) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.productUomId = productUomId;
		this.uomId = uomId;
		this.uomName = uomName;
		this.countInBase = countInBase;
		this.remainder = remainder;
	}
	
	private long id;
	
	@Override
	public long getId() {
		return id;
	}	
	
	private long productId;
	
	public long getProductId(){
		return productId;
	}
	
	private String productName;
	
	public String getProductName(){
		return productName;
	}
	
	private long productUomId;
	
	public long getProductUoMId(){
		return productUomId;
	}
	
	private long uomId;
	
	public long getUoMId(){
		return uomId;
	}
	
	private int countInBase;
	
	public int getCountInBase(){
		return countInBase;
	}
	
	private String uomName;
	
	public String getUoMName(){
		return uomName;
	}
	
	private double price;
	
	public double getItemPrice(){
		return price;
	}
	
	public double getPrice(){
		return MathHelpers.Round(price * countInBase, 2);
	}
	
	private int remainder;
	
	public int getRemainder(){
		return remainder;
	}
	
	public void setProductUnitOfMeasure(ProductUnitOfMeasure productUnitOfMeasure){
		productUomId = productUnitOfMeasure.getId();
		uomId = productUnitOfMeasure.getUnitOfMeasureId();
		uomName = productUnitOfMeasure.getUnitOfMeasureName();
		countInBase = productUnitOfMeasure.getCountInBase();
	}
}
