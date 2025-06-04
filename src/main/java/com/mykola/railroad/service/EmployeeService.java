package com.mykola.railroad.service;

import com.mykola.railroad.dto.*;
import com.mykola.railroad.dto.EmployeeSearchDTO.*;
import com.mykola.railroad.mapper.EmployeeMapper;
import com.mykola.railroad.mapper.TypeACLMapper;
import com.mykola.railroad.mapper.TypeSexMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mykola.railroad.db.public_.Tables.EMPLOYEE;
import static com.mykola.railroad.db.public_.Tables.EMPLOYEE_ACL;
import static org.jooq.impl.DSL.currentDate;
import static org.jooq.impl.DSL.year;

@Service
public class EmployeeService {
    @Autowired
    private DSLContext dsl;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private TypeACLMapper aclMapper;
    @Autowired
    private TypeSexMapper sexMapper;
    @Autowired
    private LoginService loginService;

    public List<EmployeeDTO> findAllEmployees() {
        return dsl.selectFrom(EMPLOYEE).fetch()
                .map(employeeMapper::toDto);
    }

    public List<TypeACL> getEmployeeACLs(Integer employeeId) {
        return dsl
                .select(EMPLOYEE_ACL.ACL)
                .from(EMPLOYEE_ACL)
                .where(EMPLOYEE_ACL.EMPLOYEE.eq(employeeId))
                .fetch()
                .map(r -> aclMapper.toDto(r.get(EMPLOYEE_ACL.ACL)));
    }

    public Optional<EmployeeDTO> findEmployeeByEmail(String email) {
        return dsl
                .selectFrom(EMPLOYEE)
                .where(EMPLOYEE.EMAIL.eq(email))
                .fetchOptional()
                .map(r -> employeeMapper.toDto(r));
    }

    public void login(LoginDTO login, HttpServletRequest req, HttpServletResponse res) {
        loginService.createSession(login.email, login.password, req, res);
    }

    public void logout(HttpServletRequest req) throws ServletException {
        loginService.destroySession(req);
    }

    public List<EmployeeDTO> search(EmployeeSearchDTO search) {
        HashSet<EmployeeDTO> employees = new HashSet<>();
        // за стажем роботи на станції,
        if (search.experience.isPresent()) {
            ByExperience experience = search.experience.get();
            employees.addAll(dsl
                    .selectFrom(EMPLOYEE)
                    .where(
                            currentDate().subtract(EMPLOYEE.EMPLOYED_AT)
                                    .div(Calendar.YEAR).cast(Integer.class)
                                    .add(EMPLOYEE.EXPERIENCE)
                            .between(experience.min, experience.max))
                    .fetch()
                    .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)))
            );
        }

        // статевою ознакою
        if (search.sex.isPresent()) {
            com.mykola.railroad.db.public_.enums.TypeSex sex = sexMapper.toJooq(search.sex.get().sex);
            employees.addAll(dsl
                    .selectFrom(EMPLOYEE)
                    .where(EMPLOYEE.SEX.eq(sex))
                    .fetch()
                    .map(employeeMapper::toDto)
            );
        }

//        // віком
//        if (search.age.isPresent()) {
//            ByAge age = search.age.get();
//            employees.addAll(dsl
//                    .selectFrom(EMPLOYEE)
//                    //.where(EMPLOYEE.)
//            );
//        }

        // error
        return employees.stream().toList();
    }
}
