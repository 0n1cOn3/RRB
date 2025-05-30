package com.mykola.railroad.mapper;

import com.mykola.railroad.dto.TypeInspection;
import com.mykola.railroad.dto.TypeTicketStatus;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeTicketStatusMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeTicketStatus toDto(com.mykola.railroad.db.public_.enums.TypeTicketSt record);

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    com.mykola.railroad.db.public_.enums.TypeTicketSt toJooq(TypeTicketStatus dto);
}
