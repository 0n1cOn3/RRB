package com.mykola.railroad.service;

import com.mykola.railroad.dto.AggregateTicketInfoDTO;
import com.mykola.railroad.dto.AvgSoldTicketDTO;
import com.mykola.railroad.mapper.TicketMapper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;
import static org.jooq.impl.DSL.*;

@Service
public class TicketService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private TicketMapper ticketMapper;

    public List<AggregateTicketInfoDTO> aggregateTicketInfo(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(fromDate, formatter);
        LocalDate to = LocalDate.parse(toDate, formatter);

        return dsl
                .select(
                        TRAIN_SERVICE.ROUTE,
                        avg(TICKET.COST).as("ticket_cost"),
                        count(TICKET.ID).as("ticket_count"),
                        select(count())
                                .from(ROUTE_STATION)
                                .where(ROUTE_STATION.ROUTE.eq(TRAIN_SERVICE.ROUTE))
                                .asField("route_length")
                )
                .from(TICKET)
                .leftJoin(TRAIN_SERVICE).on(TRAIN_SERVICE.ID.eq(TICKET.TRAIN_SERVICE))
                .where(TICKET.SOLD_AT.between(from, to))
                .groupBy(TRAIN_SERVICE.ROUTE, field("route_length"))
                .fetch()
                .map(r -> new AggregateTicketInfoDTO(
                        r.get(TRAIN_SERVICE.ROUTE, Integer.class),
                        r.get("ticket_count", Integer.class),
                        r.get("ticket_cost", Float.class),
                        r.get("route_length", Integer.class)
                ));
    }
}
