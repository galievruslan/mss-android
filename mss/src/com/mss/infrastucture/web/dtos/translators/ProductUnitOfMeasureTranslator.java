package com.mss.infrastucture.web.dtos.translators;

public class ProductUnitOfMeasureTranslator extends Translator<com.mss.infrastructure.web.dtos.ProductUnitOfMeasure, com.mss.domain.models.ProductUnitOfMeasure> {

	@Override
	public com.mss.domain.models.ProductUnitOfMeasure Translate(com.mss.infrastructure.web.dtos.ProductUnitOfMeasure dto) {
		return new com.mss.domain.models.ProductUnitOfMeasure(dto.getId(), dto.getProductId(), dto.getUnitOfMeasureId(), dto.getIsBase(), dto.getCountInBase());
	}

}
