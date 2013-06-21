package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.ProductUnitOfMeasure.TABLE_NAME)
public class ProductUnitOfMeasure extends Entity {
	
	public ProductUnitOfMeasure() {}
	
	public ProductUnitOfMeasure(int id, Product product, UnitOfMeasure unitOfMeasure, Boolean isBase, int countInBase)  {
		super(id);
		this.product = product;
		this.unitOfMeasure = unitOfMeasure;
		this.isBase = isBase;
		this.countInBase = countInBase;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.ProductUnitOfMeasure.PRODUCT_FIELD)
	private Product product;
	
	public Product getProduct(){
		return product;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.ProductUnitOfMeasure.UNIT_OF_MEASURE_FIELD)
	private UnitOfMeasure unitOfMeasure;
	
	public UnitOfMeasure getUnitOfMeasure(){
		return unitOfMeasure;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.ProductUnitOfMeasure.BASE_FIELD)
	private Boolean isBase;
	
	public Boolean getIsBase(){
		return isBase;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.DOUBLE, columnName = Constants.Tables.ProductUnitOfMeasure.COUNT_IN_BASE_FIELD)
	private int countInBase;
	
	public int getCountInBase(){
		return countInBase;
	}
}
