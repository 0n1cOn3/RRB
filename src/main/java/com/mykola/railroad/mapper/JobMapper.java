package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.JobRecord;
import com.mykola.railroad.dto.JobDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobDTO toDto(JobRecord record);
    JobRecord toJooq(JobDTO dto);
}
