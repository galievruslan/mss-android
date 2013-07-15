package com.mss.android.domain.models;

import java.math.BigDecimal;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.OrderItem.TABLE_NAME)
public class OrderItem extends Entity {
	
	public OrderItem() {}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.OrderItem.ORDER_FIELD)
	private int orderId;
	
	public int getOrderId() {
		return orderId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.OrderItem.PRODUCT_FIELD)
	private int productId;
	
	public int getProductId() {
		return productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.OrderItem.UNIT_OF_MEASURE_FIELD)
	private int unitOfMeasureId;
	
	public int getUnitOfMeasureId() {
		return unitOfMeasureId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.FLOAT, columnName = Constants.Tables.OrderItem.COUNT_FIELD)
	private float count;
	
	public float getCount(){
		return count;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BIG_DECIMAL, columnName = Constants.Tables.OrderItem.PRICE_FIELD)
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BIG_DECIMAL, columnName = Constants.Tables.OrderItem.AMOUNT_FIELD)
	private BigDecimal amount;
	
	public BigDecimal getAmount(){
		return amount;
	}
}