package com.mss.infrastructure.web.dtos.translators;

public class PreferencesTranslator extends Translator<com.mss.infrastructure.web.dtos.Preferences, com.mss.domain.models.Preferences> {

	@Override
	public com.mss.domain.models.Preferences Translate(com.mss.infrastructure.web.dtos.Preferences dto) {
		com.mss.domain.models.Preferences preferences = new com.mss.domain.models.Preferences();
		preferences.setDefaultRoutePointStatusId(dto.getDefaultRoutePointStatusId());
		preferences.setDefaultRoutePointAttendedStatusId(dto.getDefaultRoutePointAttendedStatusId());
		preferences.setDefaultPriceListId(dto.getDefaultPriceListId());
		preferences.setPhotoWidthResolution(dto.getPhotoWidthResolution());
		preferences.setPhotoHeightResolution(dto.getPhotoHeightResolution());
		preferences.setMessagePoolFrequency(dto.getMessagePoolFrequency());
		return preferences;
	}
}
