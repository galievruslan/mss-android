package com.mss.infrastructure.data;


public interface IReadonlyRepository<TEntity extends IEntity> {
	TEntity getById(long id) throws Throwable;
	Iterable<TEntity> find() throws Throwable;
}
