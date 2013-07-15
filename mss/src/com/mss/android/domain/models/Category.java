package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Category.TABLE_NAME)
public class Category extends Entity {
	
	public Category() {}
	
	public Category(int id, String name)  {
		super(id);
		this.name = name;
	}
	
	public Category(int id, String name, int parentId)  {		
		this(id, name);
		this.parentId = parentId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Category.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = Constants.Tables.Category.PARENT_CATEGORY_FIELD)
	private int parentId;
	
	public int getParentId(){
		return parentId;
	}
}
