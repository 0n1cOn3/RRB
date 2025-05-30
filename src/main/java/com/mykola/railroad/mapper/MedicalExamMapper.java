package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.MedicalExamRecord;
import com.mykola.railroad.dto.MedicalExamDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalExamMapper {
    MedicalExamDTO toDto(MedicalExamRecord record);
    MedicalExamRecord toJooq(MedicalExamDTO dto);
}
