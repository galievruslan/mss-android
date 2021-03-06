package com.mss.infrastructure.web.dtos.translators;

public class ProductTranslator extends Translator<com.mss.infrastructure.web.dtos.Product, com.mss.domain.models.Product> {

	@Override
	public com.mss.domain.models.Product Translate(com.mss.infrastructure.web.dtos.Product dto) {
		return new com.mss.domain.models.Product(dto.getId(), dto.getName(), dto.getCategoryId());
	}

}
