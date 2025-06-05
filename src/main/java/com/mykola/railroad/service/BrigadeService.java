package com.mykola.railroad.service;

import com.mykola.railroad.dto.AvgSalaryDTO;
import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.dto.EmployeeSearchDTO;
import com.mykola.railroad.dto.MedicalExamSearchDTO;
import com.mykola.railroad.mapper.EmployeeMapper;
import com.mykola.railroad.mapper.TypeSexMapper;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.SelectConditionStep;
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
    @Autowired
    private TypeSexMapper sexMapper;

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

    // Отримати перелік і загальне число водіїв локомотивів, які пройшли медогляд або не пройшли медогляд
    //   у вказаний рік, за статевою ознакою, віком, розміром заробітної плати.
    public List<EmployeeDTO> searchDriverMedicalExams(MedicalExamSearchDTO search) {
        HashSet<EmployeeDTO> employees = new HashSet<>();

        var selection = dsl
                .select()
                .from(TRAIN)
                .join(BRIGADE_EMPLOYEES).on(BRIGADE_EMPLOYEES.BRIGADE.eq(TRAIN.SERVICE_BRIGADE))
                .join(EMPLOYEE).on(EMPLOYEE.ID.eq(BRIGADE_EMPLOYEES.EMPLOYEE))
                .join(MEDICAL_EXAM).on(MEDICAL_EXAM.EMPLOYEE.eq(EMPLOYEE.ID));


        // за статевою ознакою, віком, розміром заробітної плати
        SelectConditionStep<org.jooq.Record> step = null;
        if (search.employee.sex.isPresent()) {
            // за статевою ознакою
            com.mykola.railroad.db.public_.enums.TypeSex sex = sexMapper.toJooq(search.employee.sex.get().sex);

            step = selection.where(EMPLOYEE.SEX.eq(sex));
        } else if (search.employee.age.isPresent()) {
            // віком
            EmployeeSearchDTO.ByAge age = search.employee.age.get();
            step = selection
                    .where(
                            DSL.extract(currentDate(), DatePart.YEAR)
                                    .subtract(DSL.extract(EMPLOYEE.BIRTHDAY, DatePart.YEAR))
                                    .cast(Integer.class)
                                    .between(age.min, age.max)
                    );
        } else if (search.employee.salary.isPresent()) {
            // розміром заробітної плати
            EmployeeSearchDTO.BySalary salary = search.employee.salary.get();
            step = selection
                    .join(JOB).on(JOB.ID.eq(EMPLOYEE.JOB))
                    .where(
                            JOB.SALARY.add(EMPLOYEE.SALARY_BONUS)
                                    .between(new BigDecimal(salary.min), new BigDecimal(salary.max))
                    );
        }

        // які пройшли медогляд або не пройшли медогляд у вказаний рік
        if (step != null) {
            // if we already chaining employee search
            employees.addAll(step
                    .and(MEDICAL_EXAM.VERDICT_GOOD.eq(search.verdict))
                    .and(DSL.extract(MEDICAL_EXAM.CREATED_AT, DatePart.YEAR)
                            .cast(Integer.class)
                            .eq(search.year)
                    )
                    .fetch()
                    .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)))
            );
        } else {
            employees.addAll(selection
                    .where(
                            MEDICAL_EXAM.VERDICT_GOOD.eq(search.verdict)
                            .and(DSL.extract(MEDICAL_EXAM.CREATED_AT, DatePart.YEAR)
                                    .cast(Integer.class)
                                    .eq(search.year)
                            )
                    )
                    .fetch()
                    .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)))
            );
        }

        return employees.stream().toList();
    }
}
