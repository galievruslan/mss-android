package com.mss.infrastructure.ormlite;

import com.j256.ormlite.dao.Dao;
import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.data.IRepository;

public class OrmliteGenericRepository<TEntity extends IEntity> implements IRepository<TEntity> {

	protected Dao<TEntity, Integer> dao = null;
	protected OrmliteGenericRepository(Dao<TEntity, Integer> dao) {
		this.dao = dao;
	}
	
	public TEntity getById(long id) throws Exception {
		return dao.queryForId((int)id);
	}

	public Iterable<TEntity> find() throws Exception {
		return dao.queryForAll();
	}

	public void save(TEntity entity) throws Exception {
		dao.createOrUpdate(entity);		
	}

	public void delete(TEntity entity) throws Exception {
		dao.delete(entity);		
	}
}
