package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Product.TABLE_NAME)
public class Product extends Entity {
	
	public Product() {}
	
	public Product(int id, String name, int categoryId)  {
		super(id);
		this.name = name;
		this.categoryId = categoryId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Product.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = Constants.Tables.Product.CATEGORY_FIELD)
	private int categoryId;
	
	public int getCategoryId(){
		return categoryId;
	}
}
