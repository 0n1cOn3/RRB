package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.StationRecord;
import com.mykola.railroad.dto.StationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationDTO toDto(StationRecord record);
    StationRecord toJooq(StationDTO dto);
}
