package com.mykola.railroad.service;

import com.mykola.railroad.db.public_.enums.TypeInspection;
import com.mykola.railroad.dto.InspectionSearchDTO;
import com.mykola.railroad.dto.TrainDTO;
import com.mykola.railroad.mapper.TrainMapper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mykola.railroad.db.public_.Tables.INSPECTION;
import static com.mykola.railroad.db.public_.Tables.TRAIN;

@Service
public class TrainService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private TrainMapper trainMapper;

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
}
