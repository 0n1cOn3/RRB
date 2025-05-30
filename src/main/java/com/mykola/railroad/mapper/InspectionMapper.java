package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.InspectionRecord;
import com.mykola.railroad.dto.InspectionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InspectionMapper {
    InspectionDTO toDto(InspectionRecord record);
    InspectionRecord toJooq(InspectionDTO dto);
}
