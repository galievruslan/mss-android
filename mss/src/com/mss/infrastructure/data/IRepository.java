package com.mss.infrastructure.data;


public interface IRepository<TEntity extends IEntity> {
	TEntity getById(long id) throws Throwable;
	Iterable<TEntity> find() throws Throwable;
	
	void save(TEntity entity) throws Throwable;
	void delete(TEntity entity) throws Throwable;
}
