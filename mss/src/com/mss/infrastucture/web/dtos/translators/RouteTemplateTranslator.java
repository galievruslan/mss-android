package com.mss.infrastucture.web.dtos.translators;

import com.mss.domain.models.Week;
import com.mss.domain.models.Week.Days;

public class RouteTemplateTranslator extends Translator<com.mss.infrastructure.web.dtos.RouteTemplate, com.mss.domain.models.RouteTemplate> {

	@Override
	public com.mss.domain.models.RouteTemplate Translate(com.mss.infrastructure.web.dtos.RouteTemplate dto) {
		Week.Days dayOfWeek = Days.Monday;
		switch (dto.getDayOfWeekNo()){
		case 1: 
			dayOfWeek = Days.Monday;
			break;
		case 2:
			dayOfWeek = Days.Tuesday;
			break;
		case 3:
			dayOfWeek = Days.Wednesday;
			break;
		case 4:
			dayOfWeek = Days.Thursday;
			break;
		case 5:
			dayOfWeek = Days.Friday;
			break;
		case 6:
			dayOfWeek = Days.Saturday;
			break;
		case 7:
			dayOfWeek = Days.Sunday;
			break;
		}
		
		return new com.mss.domain.models.RouteTemplate(dto.getId(), dayOfWeek);
	}

}
