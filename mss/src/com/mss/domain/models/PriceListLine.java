package com.mss.domain.models;

import java.math.BigDecimal;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.PriceListLine.TABLE_NAME)
public class PriceListLine extends Entity {
	
	public PriceListLine() {}
	
	public PriceListLine(int id, int priceListId, int productId, BigDecimal price)  {
		super(id);
		this.priceListId = priceListId;
		this.productId = productId;
		this.price = price;		
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.PriceListLine.PRICE_LIST_FIELD)
	private int priceListId;
	
	public int getPriceListId(){
		return priceListId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.PriceListLine.PRODUCT_FIELD)
	private int productId;
	
	public int getProductId(){
		return productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BIG_DECIMAL, columnName = Constants.Tables.PriceListLine.PRICE_FIELD)
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}	
}
