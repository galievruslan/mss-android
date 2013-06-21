package com.mss.android.domain.models;

import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.Category.TABLE_NAME)
public class Category extends Entity {
	
	public Category() {}
	
	public Category(int id, String name)  {
		super(id);
		this.name = name;
		categories = new ArrayList<Category>();
	}
	
	public Category(int id, String name, Category parentCategory)  {		
		this(id, name);
		this.parentCategory = parentCategory;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.Category.NAME_FIELD)
	private String name;
	
	public String getName(){
		return name;
	}
	
	@DatabaseField(foreign=true, columnName=Constants.Tables.Category.PARENT_CATEGORY_FIELD)
	private Category parentCategory;
	
	@ForeignCollectionField
	private Collection<Category> categories;
	
	public void AddChildCategory(Category category){
		categories.add(category);		
	}
}
