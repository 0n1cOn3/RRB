package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.TrainServiceRecord;
import com.mykola.railroad.dto.TrainServiceDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {TypeTrainStatusMapper.class, TimeDateMapper.class}, componentModel = "spring")
public interface TrainServiceMapper {
    TrainServiceDTO toDto(TrainServiceRecord record);
    TrainServiceRecord toJooq(TrainServiceDTO dto);
}
