package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.ProductRemainder.TABLE_NAME)
public class ProductRemainder extends Entity {
	
	public ProductRemainder() {}
	
	public ProductRemainder(long id, long productId, long warehouseId, long unitOfMeasureId, int quantity)  {
		super(id);
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.unitOfMeasureId = unitOfMeasureId;
		this.quantity = quantity;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.ProductRemainder.PRODUCT_FIELD)
	private long productId;
	
	public long getProductId(){
		return productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.ProductRemainder.WAREHOUSE_FIELD)
	private long warehouseId;
	
	public long getWarehouseId(){
		return warehouseId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.ProductRemainder.UNIT_OF_MEASURE_FIELD)
	private long unitOfMeasureId;
	
	public long getUnitOfMeasureId(){
		return unitOfMeasureId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.ProductRemainder.QUANTITY_FIELD)
	private int quantity;
	
	public int getQuantity(){
		return quantity;
	}
}
