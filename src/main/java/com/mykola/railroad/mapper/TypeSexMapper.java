package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypeInspection;
import com.mykola.railroad.dto.TypeSex;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeSexMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeSex toDto(com.mykola.railroad.db.public_.enums.TypeSex record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypeSex toJooq(TypeSex dto);
}
