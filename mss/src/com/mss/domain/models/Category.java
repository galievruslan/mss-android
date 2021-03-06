package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Category.TABLE_NAME)
public class Category extends Entity {
	
	public Category() {}
	
	public Category(long id, String name)  {
		super(id);
		this.name = name;
	}
	
	public Category(long id, String name, long parentId)  {		
		this(id, name);
		this.parentId = parentId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Category.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(canBeNull = true, dataType = DataType.LONG, columnName = Constants.Tables.Category.PARENT_CATEGORY_FIELD)
	private long parentId;
	
	public long getParentId(){
		return parentId;
	}
}
