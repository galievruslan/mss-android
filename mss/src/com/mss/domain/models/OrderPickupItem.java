package com.mss.domain.models;

import java.math.BigDecimal;

import com.mss.infrastructure.data.IEntity;

public class OrderPickupItem implements IEntity {
	
	public OrderPickupItem(long id, long productId, String productName, BigDecimal price) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
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
	
	private long uomId;
	
	public long getUoMId(){
		return uomId;
	}
	
	public void setUoMId(long uomId){
		this.uomId = uomId;
	}
	
	private double countInBase;
	
	public double getCountInBase(){
		return countInBase;
	}
	
	public void setCountInBase(double countInBase){
		this.countInBase = countInBase;
	}
	
	private String uomName;
	
	public String getUoMName(){
		return uomName;
	}
	
	public void setUoMName(String uomName){
		this.uomName = uomName;
	}
	
	private int count;
	
	public int getCount(){
		return count;
	}
	
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
	
	public BigDecimal getAmount(){
		return price.multiply(new BigDecimal(count)).multiply(new BigDecimal(countInBase));
	}
}
