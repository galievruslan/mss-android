package com.mss.domain.models;

import java.math.BigDecimal;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.OrderItem.TABLE_NAME)
public class OrderItem extends Entity {
	
	public OrderItem() {}
	
	public OrderItem(long orderId) {
		this.orderId = orderId;
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
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.OrderItem.PRODUCT_NAME_FIELD)
	private String productName;
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.OrderItem.PRODUCT_UNIT_OF_MEASURE_FIELD)
	private long productUnitOfMeasureId;
	
	public long getProductUnitOfMeasureId() {
		return productUnitOfMeasureId;
	}
	
	public void setProductUnitOfMeasureId(long productUnitOfMeasureId) {
		this.productUnitOfMeasureId = productUnitOfMeasureId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.OrderItem.UNIT_OF_MEASURE_FIELD)
	private long unitOfMeasureId;
	
	public long getUnitOfMeasureId() {
		return unitOfMeasureId;
	}
	
	public void setUnitOfMeasureId(long unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.OrderItem.UNIT_OF_MEASURE_NAME_FIELD)
	private String unitOfMeasureName;
	
	public String getUnitOfMeasureName() {
		return unitOfMeasureName;
	}
	
	public void setUnitOfMeasureName(String unitOfMeasureName) {
		this.unitOfMeasureName = unitOfMeasureName;
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