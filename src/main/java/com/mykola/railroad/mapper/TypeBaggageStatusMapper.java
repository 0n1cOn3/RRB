package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.enums.TypeBaggageSt;
import com.mykola.railroad.dto.TypeBaggageStatus;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeBaggageStatusMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeBaggageStatus toDto(TypeBaggageSt record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    TypeBaggageSt toJooq(TypeBaggageStatus dto);
}
