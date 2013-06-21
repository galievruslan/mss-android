package com.mss.android.domain.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.PriceListLine.TABLE_NAME)
public class PriceListLine extends Entity {
	
	public PriceListLine() {}
	
	public PriceListLine(int id, PriceList priceList, Product product, BigDecimal price)  {
		super(id);
		this.priceList = priceList;
		this.product = product;
		this.price = price;
		
		this.lines = new ArrayList<PriceListLine>();
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.PriceListLine.PRICE_LIST_FIELD)
	private PriceList priceList;
	
	public PriceList getPriceList(){
		return priceList;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.PriceListLine.PRODUCT_FIELD)
	private Product product;
	
	public Product getProduct(){
		return product;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.PriceListLine.PRICE_FIELD)
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
	
	@ForeignCollectionField
	private Collection<PriceListLine> lines;
	
	public void AddLine(PriceListLine line){
		lines.add(line);		
	}
}
