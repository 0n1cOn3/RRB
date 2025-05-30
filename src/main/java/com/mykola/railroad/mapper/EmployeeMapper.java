package com.mykola.railroad.mapper;

import com.mykola.railroad.db.public_.tables.records.EmployeeRecord;
import com.mykola.railroad.dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toDto(EmployeeRecord record);
}
