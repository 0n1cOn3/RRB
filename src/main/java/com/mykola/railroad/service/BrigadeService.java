package com.mykola.railroad.service;

import com.mykola.railroad.dto.AvgSalaryDTO;
import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.dto.EmployeeSearchDTO;
import com.mykola.railroad.mapper.EmployeeMapper;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;
import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.currentDate;

@Service
public class BrigadeService {
    @Autowired
    private DSLContext dsl;

    @Autowired
    private EmployeeMapper employeeMapper;

    // Отримати перелік і загальне число працівників у бригаді, по всіх відділах
    public List<EmployeeDTO> getBrigadeEmployees(Integer brigade) {
        return dsl
                .select()
                .from(BRIGADE_EMPLOYEES)
                .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(BRIGADE_EMPLOYEES.BRIGADE.eq(brigade))
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
    }

    // Отримати перелік і загальне число працівників у бригаді, у зазначеному відділі
    public List<EmployeeDTO> getBrigadeEmployeesInDepartment(Integer brigade, Integer department) {
        return dsl
                .select()
                .from(BRIGADE_EMPLOYEES)
                .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                .join(DEPARTMENT_EMPLOYEE).on(DEPARTMENT_EMPLOYEE.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(BRIGADE_EMPLOYEES.BRIGADE.eq(brigade)
                        .and(DEPARTMENT_EMPLOYEE.DEPARTMENT.eq(department)))
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
    }

    // обслуговуючих деякий локомотив
    public List<EmployeeDTO> getTrainServiceBrigadeEmployees(Integer train) {
        return dsl
                .select()
                .from(TRAIN)
                .join(BRIGADE).on(BRIGADE.ID.eq(TRAIN.SERVICE_BRIGADE))
                .join(BRIGADE_EMPLOYEES).on(BRIGADE_EMPLOYEES.BRIGADE.eq(BRIGADE.ID))
                .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(TRAIN.ID.eq(train))
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
    }

    // за віком
        public List<EmployeeDTO> searchBrigadeEmployeesByAge(Integer brigade, EmployeeSearchDTO search) {
        HashSet<EmployeeDTO> employees = new HashSet<>();

        if (search.age.isPresent()) {
            EmployeeSearchDTO.ByAge age = search.age.get();
            employees.addAll(dsl
                    .select()
                    .from(BRIGADE_EMPLOYEES)
                    .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                    .where(BRIGADE_EMPLOYEES.BRIGADE.eq(brigade)
                            .and(
                                    DSL.extract(currentDate(), DatePart.YEAR)
                                            .subtract(DSL.extract(EMPLOYEE.BIRTHDAY, DatePart.YEAR))
                                            .cast(Integer.class)
                                            .between(age.min, age.max)
                            )
                    )
                    .fetch()
                    .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)))
            );
        }

        return employees.stream().toList();
    }

    // сумарною (середньої) зарплаті в бригаді
    public AvgSalaryDTO getAvgBrigadeSalary(Integer brigade) {
        Float avgSalary = dsl
                .select(avg(JOB.SALARY.add(EMPLOYEE.SALARY_BONUS)))
                .from(BRIGADE_EMPLOYEES)
                .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                .join(JOB).on(EMPLOYEE.JOB.eq(JOB.ID))
                .where(BRIGADE_EMPLOYEES.BRIGADE.eq(brigade))
                .fetchOne().component1().floatValue();

        return new AvgSalaryDTO(avgSalary);
    }
}
