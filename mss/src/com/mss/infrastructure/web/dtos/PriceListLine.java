package com.mss.infrastructure.web.dtos;

import java.math.BigDecimal;

public class PriceListLine extends Dto {
	
	private int priceListId;
	
	public int getPriceListId(){
		return priceListId;
	}
	
	private int productId;
	
	public int getProductId(){
		return productId;
	}
	
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
}
