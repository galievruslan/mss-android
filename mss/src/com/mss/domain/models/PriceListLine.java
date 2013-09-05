package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.PriceListLine.TABLE_NAME)
public class PriceListLine extends Entity {
	
	public PriceListLine() {}
	
	public PriceListLine(long id, long priceListId, long productId, double price)  {
		super(id);
		this.priceListId = priceListId;
		this.productId = productId;
		this.price = price;		
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.PriceListLine.PRICE_LIST_FIELD)
	private long priceListId;
	
	public long getPriceListId(){
		return priceListId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.PriceListLine.PRODUCT_FIELD)
	private long productId;
	
	public long getProductId(){
		return productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.PriceListLine.PRICE_FIELD)
	private double price;
	
	public double getPrice(){
		return price;
	}	
}
