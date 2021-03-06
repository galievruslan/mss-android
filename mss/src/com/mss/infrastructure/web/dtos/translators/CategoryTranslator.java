package com.mss.infrastructure.web.dtos.translators;

public class CategoryTranslator extends Translator<com.mss.infrastructure.web.dtos.Category, com.mss.domain.models.Category> {

	@Override
	public com.mss.domain.models.Category Translate(com.mss.infrastructure.web.dtos.Category dto) {
		return new com.mss.domain.models.Category(dto.getId(), dto.getName(), dto.getParentId());
	}

}
