package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.enums.TypeAcl;
import com.mykola.railroad.dto.TypeACL;
import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface TypeACLMapper {
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TypeACL toDto(TypeAcl record);
}
