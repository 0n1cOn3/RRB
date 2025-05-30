package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.TrainServiceRecord;
import com.mykola.railroad.dto.TrainServiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainServiceMapper {
    TrainServiceDTO toDto(TrainServiceRecord record);
    TrainServiceRecord toJooq(TrainServiceDTO dto);
}
