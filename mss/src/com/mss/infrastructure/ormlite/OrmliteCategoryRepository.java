package com.mss.infrastructure.ormlite;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Category;
import com.mss.domain.models.Constants;

public class OrmliteCategoryRepository extends OrmliteGenericRepository<Category> {

	public OrmliteCategoryRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getCategoryDao());
	}
	
	@Override
	public Iterable<Category> find() throws Throwable {
		QueryBuilder<Category, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.orderBy(Constants.Tables.Category.PARENT_CATEGORY_FIELD, true)
			.orderBy(Constants.Tables.Category.NAME_FIELD, true);
		
		return dao.queryForAll();
	}
}
