package com.mss.domain.models;

import java.math.BigDecimal;

import com.mss.infrastructure.data.IEntity;

public class OrderPickedUpItem implements IEntity {
	
	public OrderPickedUpItem(long id, 
			String name, 
			BigDecimal price, 
			int count,
			ProductUnitOfMeasure productUnitOfMeasure) {
		this(id, name, price, count, 
				productUnitOfMeasure.getId(), 
				productUnitOfMeasure.getUnitOfMeasureId(),
				productUnitOfMeasure.getUnitOfMeasureName(),
				productUnitOfMeasure.getCountInBase());
	}
	
	public OrderPickedUpItem(long id, 
			String name, 
			BigDecimal price, 
			int count,
			long productUomId,
			long uomId,
			String uomName,
			int countInBase) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.productUomId = productUomId;
		this.uomId = uomId;
		this.uomName = uomName;
		this.countInBase = countInBase;
	}
	
	private long id;
	
	@Override
	public long getId() {
		return id;
	}	
	
	private String name;
	
	public String getName(){
		return name;
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
	
	private int count;
	
	public int getCount(){
		return count;
	}
	
	private BigDecimal price;
	
	public BigDecimal getItemPrice(){
		return price;
	}
	
	public BigDecimal getPrice(){
		return price.multiply(new BigDecimal(countInBase));
	}
	
	public BigDecimal getAmount(){
		return price.multiply(new BigDecimal(count)).multiply(new BigDecimal(countInBase));
	}
	
	public void setProductUnitOfMeasure(ProductUnitOfMeasure productUnitOfMeasure) {
		productUomId = productUnitOfMeasure.getId();
		uomId = productUnitOfMeasure.getUnitOfMeasureId();
		uomName = productUnitOfMeasure.getUnitOfMeasureName();
		countInBase = productUnitOfMeasure.getCountInBase();
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
