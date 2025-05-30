package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypeInspection;
import com.mykola.railroad.dto.TypeTrainStatus;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeTrainStatusMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeTrainStatus toDto(com.mykola.railroad.db.public_.enums.TypeTrainSt record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypeTrainSt toJooq(TypeTrainStatus dto);
}
