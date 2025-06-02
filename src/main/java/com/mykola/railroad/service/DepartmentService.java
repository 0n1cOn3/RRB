package com.mykola.railroad.service;

import com.mykola.railroad.db.public_.tables.records.EmployeeRecord;
import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.mapper.EmployeeMapper;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mykola.railroad.db.public_.tables.DepartmentEmployee.DEPARTMENT_EMPLOYEE;
import static com.mykola.railroad.db.public_.tables.Employee.EMPLOYEE;

@Service
public class DepartmentService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private EmployeeMapper employeeMapper;

    public Map<Integer, EmployeeDTO> getDepartmentHeads() {
        return dsl
                .select()
                .from(DEPARTMENT_EMPLOYEE)
                .innerJoin(EMPLOYEE).on(DEPARTMENT_EMPLOYEE.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(DEPARTMENT_EMPLOYEE.HEAD.eq(true))
                .fetchMap(r -> r.into(DEPARTMENT_EMPLOYEE.DEPARTMENT).into(Integer.class),
                    r -> employeeMapper.toDto(r.into(EmployeeRecord.class))
                );
    }

    public List<EmployeeDTO> getDepartmentEmployees(Integer department) {
        return dsl
                .select()
                .from(DEPARTMENT_EMPLOYEE)
                .innerJoin(EMPLOYEE).on(DEPARTMENT_EMPLOYEE.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(DEPARTMENT_EMPLOYEE.DEPARTMENT.eq(department))
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
    }
}
