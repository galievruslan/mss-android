package com.mss.android.domain.models;

import com.j256.ormlite.field.*;
import com.mss.android.infrastructure.data.IEntity;

/**
 * 
 * @author golfarid
 *
 *<p>Represent base class for domain model object</p>
 */
public abstract class Entity implements IEntity {
	
	protected Entity(){}
	
	public Entity(int id){
		this.id = id;
	}
	
	@DatabaseField(generatedId=true, allowGeneratedIdInsert=true, columnName = Constants.Tables.Entity.ID_FIELD)
	protected int id;
	
	public int getId(){
		return id;
	}
}
