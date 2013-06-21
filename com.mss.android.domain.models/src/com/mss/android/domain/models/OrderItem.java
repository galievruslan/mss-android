package com.mss.android.domain.models;

import java.math.BigDecimal;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.OrderItem.TABLE_NAME)
public class OrderItem extends Entity {
	
	public OrderItem() {}
	
	@DatabaseField(id = true, columnName = Constants.Tables.Entity.ID_FIELD, generatedId = true)
	protected Override id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.OrderItem.ORDER_FIELD)
	private Order order;
		
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.OrderItem.PRODUCT_FIELD)
	private Product product;
	
	public Product getProduct() {
		return product;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.OrderItem.UNIT_OF_MEASURE_FIELD)
	private UnitOfMeasure unitOfMeasure;
	
	public UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.FLOAT, columnName = Constants.Tables.OrderItem.COUNT_FIELD)
	private Float count;
	
	public Float getCount(){
		return count;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.OrderItem.PRICE_FIELD)
	private BigDecimal price;
	
	public BigDecimal getPrice(){
		return price;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.OrderItem.AMOUNT_FIELD)
	private BigDecimal amount;
	
	public BigDecimal getAmount(){
		return amount;
	}
}