package com.mss.domain.models;

import java.math.BigDecimal;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.OrderItem.TABLE_NAME)
public class OrderItem extends Entity {
	
	public OrderItem() {}
	
	public OrderItem(long orderId, long productId) {
		this.orderId = orderId;
		this.productId = productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.OrderItem.ORDER_FIELD)
	private long orderId;
	
	public long getOrderId() {
		return orderId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.OrderItem.PRODUCT_FIELD)
	private long productId;
	
	public long getProductId() {
		return productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.OrderItem.UNIT_OF_MEASURE_FIELD)
	private long unitOfMeasureId;
	
	public long getUnitOfMeasureId() {
		return unitOfMeasureId;
	}
	
	public void setUnitOfMeasureId(long unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.OrderItem.UNIT_OF_MEASURE_COUNT_FIELD)
	private int countInUnitOfMeasure;
	
	public int getCountInUnitOfMeasure(){
		return countInUnitOfMeasure;
	}
	
	public void setCountInUnitOfMeasure(int countInUnitOfMeasure) {
		this.countInUnitOfMeasure = countInUnitOfMeasure;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.OrderItem.COUNT_FIELD)
	private int count;
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BIG_DECIMAL, columnName = Constants.Tables.OrderItem.PRICE_FIELD)
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
	
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	
	public BigDecimal getAmount(){
		return new BigDecimal(count * countInUnitOfMeasure).multiply(price);
	}
}