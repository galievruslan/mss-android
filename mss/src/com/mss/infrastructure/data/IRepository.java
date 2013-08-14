package com.mss.infrastructure.data;

public interface IRepository<TEntity extends IEntity> extends IReadonlyRepository<TEntity> {
	void save(TEntity entity) throws Throwable;
	void delete(TEntity entity) throws Throwable;
}
