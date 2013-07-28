package com.mss.infrastucture.web.dtos.translators;

public class RoutePointTemplateTranslator extends Translator<com.mss.infrastructure.web.dtos.RoutePointTemplate, com.mss.domain.models.RoutePointTemplate> {

	@Override
	public com.mss.domain.models.RoutePointTemplate Translate(com.mss.infrastructure.web.dtos.RoutePointTemplate dto) {
		return new com.mss.domain.models.RoutePointTemplate(dto.getId(), dto.getRouteTemplateId(), dto.getShippingAddressId());
	}

}
