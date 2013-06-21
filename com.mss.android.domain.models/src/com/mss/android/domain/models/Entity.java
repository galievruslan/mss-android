package com.mss.android.domain.models;

import com.j256.ormlite.field.*;

/**
 * 
 * @author golfarid
 *
 *<p>Represent base class for domain model object</p>
 */
public abstract class Entity {
	
	protected Entity(){}
	
	public Entity(int id){
		this.id = id;
	}
	
	@DatabaseField(id = true, columnName = Constants.Tables.Entity.ID_FIELD)
	protected int id;
	
	public int getId(){
		return id;
	}
}
