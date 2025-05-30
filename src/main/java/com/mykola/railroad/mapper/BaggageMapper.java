package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.Baggage;
import com.mykola.railroad.db.public_.tables.records.BaggageRecord;
import com.mykola.railroad.dto.BaggageDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {TypeBaggageStatusMapper.class}, componentModel = "spring")
public interface BaggageMapper {
    BaggageDTO toDto(BaggageRecord baggage);
    BaggageRecord toJooq(Baggage dto);
}
