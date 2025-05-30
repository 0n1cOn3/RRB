package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.BrigadeRecord;
import com.mykola.railroad.dto.BrigadeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrigadeMapper {
    BrigadeDTO toDto(BrigadeRecord brigade);
    BrigadeRecord toJooq(BrigadeDTO dto);
}
