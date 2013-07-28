package com.mss.infrastructure.web.dtos;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class PriceListLine extends Dto {
	
	@SerializedName("price_list_id")
	private long priceListId;
	
	public long getPriceListId(){
		return priceListId;
	}
	
	@SerializedName("product_id")
	private long productId;
	
	public long getProductId(){
		return productId;
	}
	
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
}
