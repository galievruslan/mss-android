package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Product.TABLE_NAME)
public class Product extends Entity {
	
	public Product() {}
	
	public Product(long id, String name, long categoryId)  {
		super(id);
		this.name = name;
		this.categoryId = categoryId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Product.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.Product.CATEGORY_FIELD)
	private long categoryId;
	
	public long getCategoryId(){
		return categoryId;
	}
}
