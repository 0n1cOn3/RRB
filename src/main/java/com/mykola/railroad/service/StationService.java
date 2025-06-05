package com.mykola.railroad.service;

import com.mykola.railroad.dto.TrainDTO;
import com.mykola.railroad.mapper.TrainMapper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;

@Service
public class StationService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private TrainMapper trainMapper;

    public List<TrainDTO> getStationTrains(Integer station) {
        return dsl
                .selectDistinct()
                .from(ROUTE_STATION)
                .join(TRAIN_SERVICE).on(TRAIN_SERVICE.ROUTE.eq(ROUTE_STATION.ROUTE))
                .join(TRAIN).on(TRAIN_SERVICE.TRAIN.eq(TRAIN.ID))
                .where(ROUTE_STATION.STATION.eq(station))
                .fetch()
                .map(r -> trainMapper.toDto(r.into(TRAIN)));
    }
}
