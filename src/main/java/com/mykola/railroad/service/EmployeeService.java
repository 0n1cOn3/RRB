package com.mykola.railroad.service;

import com.mykola.railroad.dto.EmployeeDTO;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mykola.railroad.db.public_.Tables.EMPLOYEE;
import static java.util.stream.Collectors.mapping;

@Service
public class EmployeeService {
    @Autowired
    private DSLContext dsl;

    public List<EmployeeDTO> findAllEmployees() {
        System.out.println("niiiiggererrere");
        return dsl.selectFrom(EMPLOYEE).fetchInto(EmployeeDTO.class);
    }
}
