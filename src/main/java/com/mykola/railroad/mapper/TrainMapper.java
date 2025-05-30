package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.TrainRecord;
import com.mykola.railroad.dto.TrainDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainMapper {
    TrainDTO toDto(TrainRecord record);
    TrainRecord toJooq(TrainDTO dto);
}
