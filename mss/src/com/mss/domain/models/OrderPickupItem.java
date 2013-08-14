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
	
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
}
