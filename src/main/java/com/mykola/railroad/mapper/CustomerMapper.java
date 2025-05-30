package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.CustomerRecord;
import com.mykola.railroad.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {TypeSexMapper.class, TimeDateMapper.class}, componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDto(CustomerRecord record);
    CustomerRecord toJooq(CustomerDTO dto);
}
