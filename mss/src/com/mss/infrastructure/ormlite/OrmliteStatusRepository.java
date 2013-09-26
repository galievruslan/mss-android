package com.mss.infrastructure.ormlite;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.mss.domain.models.Status;

public class OrmliteStatusRepository extends OrmliteGenericRepository<Status> {

	public OrmliteStatusRepository(DatabaseHelper databaseHelper) throws Throwable{
		super(databaseHelper.getStatusDao());
	}
	
	public Iterable<Status> find(String searchCriteria) throws Throwable {
		ArrayList<Status> filtredStatuses = new ArrayList<Status>();
		Pattern pattern = Pattern.compile(Pattern.quote(searchCriteria), Pattern.CASE_INSENSITIVE);
		
		Iterable<Status> statuses = find();
		for (Status status : statuses) {
			if (pattern.matcher(status.getName()).find())
				filtredStatuses.add(status);
		}
		
		return filtredStatuses;
	}
}
