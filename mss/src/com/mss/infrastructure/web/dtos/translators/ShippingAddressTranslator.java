package com.mss.infrastructure.web.dtos.translators;

public class ShippingAddressTranslator extends Translator<com.mss.infrastructure.web.dtos.ShippingAddress, com.mss.domain.models.ShippingAddress> {

	@Override
	public com.mss.domain.models.ShippingAddress Translate(com.mss.infrastructure.web.dtos.ShippingAddress dto) {
		return new com.mss.domain.models.ShippingAddress(dto.getId(), dto.getName(), dto.getAddress(), dto.getCustomerId());
	}
}
