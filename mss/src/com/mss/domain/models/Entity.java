package com.mss.domain.models;

import com.j256.ormlite.field.*;
import com.mss.infrastructure.data.IEntity;

/**
 * 
 * @author golfarid
 *
 *<p>Represent base class for domain model object</p>
 */
public abstract class Entity implements IEntity {
	
	protected Entity(){}
	
	public Entity(long id){
		this.id = id;
	}
	
	@DatabaseField(generatedId=true, allowGeneratedIdInsert=true, columnName = Constants.Tables.Entity.ID_FIELD)
	protected long id;
	
	public long getId(){
		return id;
	}
}
