package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.ProductUnitOfMeasure.TABLE_NAME)
public class ProductUnitOfMeasure extends Entity {
	
	public ProductUnitOfMeasure() {}
	
	public ProductUnitOfMeasure(long id, long productId, long unitOfMeasureId, Boolean isBase, double countInBase)  {
		super(id);
		this.productId = productId;
		this.unitOfMeasureId = unitOfMeasureId;
		this.isBase = isBase;
		this.countInBase = countInBase;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.ProductUnitOfMeasure.PRODUCT_FIELD)
	private long productId;
	
	public long getProductId(){
		return productId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.ProductUnitOfMeasure.UNIT_OF_MEASURE_FIELD)
	private long unitOfMeasureId;
	
	public long getUnitOfMeasureId(){
		return unitOfMeasureId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.ProductUnitOfMeasure.BASE_FIELD)
	private boolean isBase;
	
	public boolean getIsBase(){
		return isBase;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.ProductUnitOfMeasure.COUNT_IN_BASE_FIELD)
	private double countInBase;
	
	public double getCountInBase(){
		return countInBase;
	}
}
