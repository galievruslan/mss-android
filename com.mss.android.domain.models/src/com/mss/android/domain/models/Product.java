package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Product.TABLE_NAME)
public class Product extends Entity {
	
	public Product() {}
	
	public Product(int id, String name, Category category)  {
		super(id);
		this.name = name;
		this.category = category;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Product.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Constants.Tables.Product.CATEGORY_FIELD)
	private Category category;
	
	public Category getCategory(){
		return category;
	}
}
