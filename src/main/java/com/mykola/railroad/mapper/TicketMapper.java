package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.TicketRecord;
import com.mykola.railroad.dto.TicketDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {TypeTicketStatusMapper.class, TypePassengerMapper.class}, componentModel = "spring")
public interface TicketMapper {
    TicketDTO toDto(TicketRecord record);
    TicketRecord toJooq(TicketDTO dto);
}
