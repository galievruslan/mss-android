package com.mss.infrastructure.web.dtos.translators;

public class PriceListTranslator extends Translator<com.mss.infrastructure.web.dtos.PriceList, com.mss.domain.models.PriceList> {

	@Override
	public com.mss.domain.models.PriceList Translate(com.mss.infrastructure.web.dtos.PriceList dto) {
		return new com.mss.domain.models.PriceList(dto.getId(), dto.getName());
	}
}
