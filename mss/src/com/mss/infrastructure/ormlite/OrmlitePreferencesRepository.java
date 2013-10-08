package com.mss.infrastructure.ormlite;

import com.mss.domain.models.Preferences;

public class OrmlitePreferencesRepository extends OrmliteGenericRepository<Preferences> {

	public OrmlitePreferencesRepository(DatabaseHelper databaseHelper) throws Exception{
		super(databaseHelper.getPreferencesDao());
	}
}
