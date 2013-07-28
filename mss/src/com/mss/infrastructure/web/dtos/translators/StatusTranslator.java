package com.mss.infrastructure.web.dtos.translators;

public class StatusTranslator extends Translator<com.mss.infrastructure.web.dtos.Status, com.mss.domain.models.Status> {

	@Override
	public com.mss.domain.models.Status Translate(com.mss.infrastructure.web.dtos.Status dto) {
		return new com.mss.domain.models.Status(dto.getId(), dto.getName());
	}
}
