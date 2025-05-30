package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.DepartmentRecord;
import com.mykola.railroad.dto.DepartmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentDTO toDto(DepartmentRecord record);
    DepartmentRecord toJooq(DepartmentDTO dto);
}
