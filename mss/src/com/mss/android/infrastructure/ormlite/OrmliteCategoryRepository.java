package com.mss.android.infrastructure.ormlite;

import com.mss.android.domain.models.Category;

public class OrmliteCategoryRepository extends OrmliteGenericRepository<Category> {

	public OrmliteCategoryRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getCategoryDao());
	}
}