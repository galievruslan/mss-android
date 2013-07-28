package com.mss.infrastructure.web.dtos.translators;

import com.mss.infrastructure.data.IEntity;
import com.mss.infrastructure.web.dtos.Dto;

public abstract class Translator<TDto extends Dto, TModel extends IEntity> {
	public abstract TModel Translate(TDto dto);
}
