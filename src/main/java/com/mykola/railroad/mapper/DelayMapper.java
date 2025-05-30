package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.DelayRecord;
import com.mykola.railroad.dto.DelayDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {TypeDelayMapper.class, TimeDateMapper.class}, componentModel = "spring")
public interface DelayMapper {
    DelayDTO toDto(DelayRecord record);
    DelayRecord toJooq(DelayDTO dto);
}
