package com.mykola.railroad.service;

import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.dto.TypeACL;
import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mykola.railroad.db.public_.Tables.EMPLOYEE;
import static com.mykola.railroad.db.public_.Tables.EMPLOYEE_ACL;
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

    public List<TypeACL> getEmployeeACLs(Integer employeeId) {
        return dsl
                .select(EMPLOYEE_ACL.ACL)
                .from(EMPLOYEE_ACL)
                .where(EMPLOYEE_ACL.EMPLOYEE.eq(employeeId))
                .fetch()
                .map(acl -> modelMapper.map(acl, TypeACL.class));
    }
}
