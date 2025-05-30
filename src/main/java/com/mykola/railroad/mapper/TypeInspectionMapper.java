package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypeInspection;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeInspectionMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeInspection toDto(com.mykola.railroad.db.public_.enums.TypeInspection record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypeInspection toJooq(TypeInspection dto);
}
