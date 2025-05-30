package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.RouteRecord;
import com.mykola.railroad.dto.RouteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteDTO toDto(RouteRecord record);
    RouteRecord toJooq(RouteDTO dto);
}
