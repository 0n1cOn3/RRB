package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypePassenger;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypePassengerMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypePassenger toDto(com.mykola.railroad.db.public_.enums.TypePassenger record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypePassenger toJooq(TypePassenger dto);
}
