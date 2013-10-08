package com.mss.infrastructure.web.dtos.translators;

public class CustomerTranslator extends Translator<com.mss.infrastructure.web.dtos.Customer, com.mss.domain.models.Customer> {

	@Override
	public com.mss.domain.models.Customer Translate(com.mss.infrastructure.web.dtos.Customer dto) {
		return new com.mss.domain.models.Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getDebt());
	}
}
