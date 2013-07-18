package com.mss.infrastructure.ormlite;

import com.mss.domain.models.Category;

public class OrmliteCategoryRepository extends OrmliteGenericRepository<Category> {

	public OrmliteCategoryRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getCategoryDao());
	}
}
