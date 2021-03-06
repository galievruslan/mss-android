package com.mss.infrastructure.ormlite;

import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.stmt.QueryBuilder;
import com.mss.domain.models.Category;
import com.mss.domain.models.Constants;

public class OrmliteCategoryRepository extends OrmliteGenericRepository<Category> {

	public OrmliteCategoryRepository(DatabaseHelper databaseHelper) throws Exception {
		super(databaseHelper.getCategoryDao());
	}
	
	@Override
	public Iterable<Category> find() throws Exception {
		QueryBuilder<Category, Integer> queryBuilder = dao.queryBuilder();
		
		queryBuilder.orderBy(Constants.Tables.Category.PARENT_CATEGORY_FIELD, true)
			.orderBy(Constants.Tables.Category.NAME_FIELD, true);
		
		return dao.query(queryBuilder.prepare());
	}
	
	public Iterable<Category> find(long[] ids) throws Exception {
		QueryBuilder<Category, Integer> queryBuilder = dao.queryBuilder();
		Set<Long> idSet = new HashSet<Long>();
		for (long id : ids) {
			idSet.add(id);
		}
		
		queryBuilder.where().in(Constants.Tables.Entity.ID_FIELD, idSet);
		
		return dao.query(queryBuilder.prepare());
	}
}
