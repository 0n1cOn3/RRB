package com.mykola.railroad.service;

import com.mykola.railroad.dto.DelayServiceDTO;
import com.mykola.railroad.dto.TrainServiceDTO;
import com.mykola.railroad.dto.TypeDelay;
import com.mykola.railroad.mapper.DelayMapper;
import com.mykola.railroad.mapper.TrainServiceMapper;
import com.mykola.railroad.mapper.TypeDelayMapper;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
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
    @Autowired
    private TypeDelayMapper typeDelayMapper;
    @Autowired
    private DelayMapper delayMapper;

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

    public List<DelayServiceDTO> getDelayServices(String delayTypeName, Integer route) {
        var selectClause = dsl
                .select()
                .from(DELAY)
                .join(TRAIN_SERVICE).on(DELAY.DELAY_SERVICE.eq(TRAIN_SERVICE.ID));

        SelectConditionStep<?> whereClause = null;
        if (delayTypeName != null) {
            TypeDelay delay = TypeDelay.valueOf(delayTypeName);
            whereClause = selectClause
                    .where(DELAY.DELAY_TYPE.eq(typeDelayMapper.toJooq(delay)));
        } else if (route != null) {
            whereClause = selectClause
                    .where(TRAIN_SERVICE.ROUTE.eq(route));
        }

        return whereClause
                .fetch()
                .map(r -> new DelayServiceDTO(
                        delayMapper.toDto(r.into(DELAY)),
                        trainServiceMapper.toDto(r.into(TRAIN_SERVICE))
                ));
    }
}
