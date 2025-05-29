package com.mykola.railroad.service;

import com.mykola.railroad.dto.EmployeeDTO;
import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mykola.railroad.db.public_.Tables.EMPLOYEE;
import static java.util.stream.Collectors.mapping;

@Service
public class EmployeeService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private ModelMapper modelMapper;

    public List<EmployeeDTO> findAllEmployees() {
        return dsl.selectFrom(EMPLOYEE).fetch()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }
}
