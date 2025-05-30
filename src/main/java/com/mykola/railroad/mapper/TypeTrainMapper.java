package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypeTrain;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeTrainMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeTrain toDto(com.mykola.railroad.db.public_.enums.TypeTrain record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypeTrain toJooq(TypeTrain dto);
}
