package com.mss.domain.models;

import java.util.UUID;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Constants.Tables.RoutePointPhoto.TABLE_NAME)
public class RoutePointPhoto extends Entity {
	
	public RoutePointPhoto() {}
	
	public RoutePointPhoto(long routePointId, String path)  {
		this.routePointId = routePointId;
		this.path = path;
		this.uid = UUID.randomUUID();
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = Constants.Tables.RoutePointPhoto.ROUTE_POINT_FIELD)
	private long routePointId;
	
	public long getRoutePointId(){
		return routePointId;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.RoutePointPhoto.PATH_FIELD)
	private String path;
	
	public String getPath(){
		return path;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = Constants.Tables.RoutePointPhoto.COMMENT_FIELD)
	private String comment;
	
	public String getComment(){
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	@DatabaseField(canBeNull = false, dataType = DataType.UUID, columnName = Constants.Tables.RoutePointPhoto.UID_FIELD)
	private UUID uid;
	
	public UUID getUid(){
		return uid;
	}
}
