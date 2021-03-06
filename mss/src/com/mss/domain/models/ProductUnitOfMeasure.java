package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.ProductUnitOfMeasure.TABLE_NAME)
public class ProductUnitOfMeasure extends Entity {
	
	public ProductUnitOfMeasure() {}
	
	public ProductUnitOfMeasure(long id, long productId, long unitOfMeasureId, Boolean isBase, int countInBase)  {
		super(id);
		this.productId = productId;
		this.unitOfMeasureId = unitOfMeasureId;
		this.isBase = isBase;
		this.countInBase = countInBase;
	}
	
	public ProductUnitOfMeasure(long id, long productId, long unitOfMeasureId, String unitOfMeasureName, Boolean isBase, int countInBase)  {
		super(id);
		this.productId = productId;
		this.unitOfMeasureId = unitOfMeasureId;
		this.unitOfMeasureName = unitOfMeasureName;
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
	
	private String unitOfMeasureName;
	
	public String getUnitOfMeasureName(){
		return unitOfMeasureName;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = Constants.Tables.ProductUnitOfMeasure.BASE_FIELD)
	private boolean isBase;
	
	public boolean getIsBase(){
		return isBase;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.ProductUnitOfMeasure.COUNT_IN_BASE_FIELD)
	private int countInBase;
	
	public int getCountInBase(){
		return countInBase;
	}
}
