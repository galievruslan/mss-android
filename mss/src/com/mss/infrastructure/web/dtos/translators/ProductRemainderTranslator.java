package com.mss.infrastructure.web.dtos.translators;

public class ProductRemainderTranslator extends Translator<com.mss.infrastructure.web.dtos.ProductRemainder, com.mss.domain.models.ProductRemainder> {

	@Override
	public com.mss.domain.models.ProductRemainder Translate(com.mss.infrastructure.web.dtos.ProductRemainder dto) {
		return new com.mss.domain.models.ProductRemainder(dto.getId(), dto.getProductId(), dto.getWarehouseId(), dto.getUnitOfMeasureId(), dto.getQuantity());
	}

}
