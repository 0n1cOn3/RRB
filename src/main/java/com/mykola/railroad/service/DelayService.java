package com.mykola.railroad.service;

import com.mykola.railroad.dto.TrainServiceDTO;
import com.mykola.railroad.mapper.TrainServiceMapper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;
import static org.jooq.impl.DSL.select;

@Service
public class DelayService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private TrainServiceMapper trainServiceMapper;

    public List<TrainServiceDTO> getCanceledServicesOnRoute(Integer route) {
        return dsl
                .select()
                .from(DELAY)
                .join(TRAIN_SERVICE).on(DELAY.DELAY_SERVICE.eq(TRAIN_SERVICE.ID))
                .where(DELAY.CANCEL_SERVICE.eq(true)
                        .and(TRAIN_SERVICE.ROUTE.eq(route))
                )
                .fetch()
                .map(r -> trainServiceMapper.toDto(r.into(TRAIN_SERVICE)));
    }

    public List<TrainServiceDTO> getCanceledServicesToStation(Integer station) {
        return dsl
                .select()
                .from(DELAY)
                .join(TRAIN_SERVICE).on(DELAY.DELAY_SERVICE.eq(TRAIN_SERVICE.ID))
                .where(DELAY.CANCEL_SERVICE.eq(true)
                        .and(TRAIN_SERVICE.ROUTE.in(
                                select(ROUTE_STATION.ROUTE)
                                        .from(ROUTE_STATION)
                                        .where(ROUTE_STATION.STATION.eq(station))
                        ))
                )
                .fetch()
                .map(r -> trainServiceMapper.toDto(r.into(TRAIN_SERVICE)));
    }
}
