package com.mss.infrastucture.web.dtos.translators;

public class WarehouseTranslator extends Translator<com.mss.infrastructure.web.dtos.Warehouse, com.mss.domain.models.Warehouse> {

	@Override
	public com.mss.domain.models.Warehouse Translate(com.mss.infrastructure.web.dtos.Warehouse dto) {
		return new com.mss.domain.models.Warehouse(dto.getId(), dto.getName(), dto.getAddress(), false);
	}
}
