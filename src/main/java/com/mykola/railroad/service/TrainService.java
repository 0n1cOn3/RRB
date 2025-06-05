package com.mykola.railroad.service;

import com.mykola.railroad.db.public_.enums.TypeInspection;
import com.mykola.railroad.dto.AggregateTrainInfoDTO;
import com.mykola.railroad.dto.InspectionSearchDTO;
import com.mykola.railroad.dto.TrainDTO;
import com.mykola.railroad.mapper.TrainMapper;
import com.mykola.railroad.mapper.TrainServiceMapper;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.select;

@Service
public class TrainService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private TrainServiceMapper trainServiceMapper;


    public List<TrainDTO> searchInspectedTrains(InspectionSearchDTO search) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(search.from, formatter);
        LocalDate to = LocalDate.parse(search.to, formatter);

        return dsl
                .select()
                .from(INSPECTION)
                .join(TRAIN).on(TRAIN.ID.eq(INSPECTION.TRAIN))
                .where(INSPECTION.TYPE.eq(TypeInspection.planned)
                        .and(INSPECTION.INSPECTED_AT.between(from, to))
                )
                .fetch()
                .map(r -> trainMapper.toDto(r.into(TRAIN)));
    }

    public List<AggregateTrainInfoDTO> aggregateTrainInfo() {
        return dsl
                .select(DSL.asterisk(), select(count())
                        .from(ROUTE_STATION)
                        .where(ROUTE_STATION.ROUTE.eq(TRAIN_SERVICE.ROUTE))
                        .asField("route_length")
                )
                .from(TRAIN)
                .join(TRAIN_SERVICE).on(TRAIN_SERVICE.TRAIN.eq(TRAIN.ID))
                .fetch()
                .map(r -> new AggregateTrainInfoDTO(
                        trainMapper.toDto(r.into(TRAIN)),
                        trainServiceMapper.toDto(r.into(TRAIN_SERVICE)),
                        r.get("route_length", Integer.class),
                        null)
                );
    }
}
