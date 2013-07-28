package com.mss.infrastructure.web.dtos.translators;

public class PriceListLineTranslator extends Translator<com.mss.infrastructure.web.dtos.PriceListLine, com.mss.domain.models.PriceListLine> {

	@Override
	public com.mss.domain.models.PriceListLine Translate(com.mss.infrastructure.web.dtos.PriceListLine dto) {
		return new com.mss.domain.models.PriceListLine(dto.getId(), dto.getPriceListId(), dto.getProductId(), dto.getPrice());
	}
}
