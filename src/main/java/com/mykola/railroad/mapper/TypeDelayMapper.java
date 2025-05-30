package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypeDelay;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(uses = {TimeDateMapper.class}, componentModel = "spring")
public interface TypeDelayMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeDelay toDto(com.mykola.railroad.db.public_.enums.TypeDelay record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypeDelay toJooq(TypeDelay dto);
}
