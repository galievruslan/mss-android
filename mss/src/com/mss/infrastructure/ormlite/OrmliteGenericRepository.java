package com.mss.infrastructure.ormlite;

import com.j256.ormlite.dao.Dao;
import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.data.IRepository;

public class OrmliteGenericRepository<TEntity extends IEntity> implements IRepository<TEntity> {

	protected Dao<TEntity, Integer> dao = null;
	protected OrmliteGenericRepository(Dao<TEntity, Integer> dao) throws Throwable{
		this.dao = dao;
	}
	
	public TEntity getById(long id) throws Throwable {
		return dao.queryForId((int)id);
	}

	public Iterable<TEntity> find() throws Throwable {
		return dao.queryForAll();
	}

	public void save(TEntity entity) throws Throwable {
		dao.createOrUpdate(entity);		
	}

	public void delete(TEntity entity) throws Throwable {
		dao.delete(entity);		
	}
}