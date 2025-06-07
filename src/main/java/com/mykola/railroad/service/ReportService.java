package com.mykola.railroad.service;

import com.mykola.railroad.dto.*;
import com.mykola.railroad.mapper.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;

@Service
public class ReportService {
    @Autowired
    private DSLContext dsl;
    @Autowired private EmployeeMapper employeeMapper;
    @Autowired private TrainMapper trainMapper;
    @Autowired private TrainServiceMapper trainServiceMapper;
    @Autowired private DelayMapper delayMapper;
    @Autowired private CustomerMapper customerMapper;
    @Autowired private TicketMapper ticketMapper;
    @Autowired private TypeSexMapper typeSexMapper;
    @Autowired private RouteMapper routeMapper;

    /** Task 1 */
    public ListResult<EmployeeDTO> employees(Integer departmentId, boolean headsOnly, EmployeeSearchDTO search) {
        Condition c = DSL.trueCondition();
        if (departmentId != null) {
            c = c.and(EMPLOYEE.ID.in(DSL.select(DEPARTMENT_EMPLOYEE.EMPLOYEE).from(DEPARTMENT_EMPLOYEE)
                    .where(DEPARTMENT_EMPLOYEE.DEPARTMENT.eq(departmentId))));
        }
        if (headsOnly) {
            c = c.and(EMPLOYEE.ID.in(DSL.select(DEPARTMENT_EMPLOYEE.EMPLOYEE).from(DEPARTMENT_EMPLOYEE)
                    .where(DEPARTMENT_EMPLOYEE.HEAD.isTrue())));
        }
        if (search != null) {
            if (search.getExperience().isPresent()) {
                var ex = search.getExperience().get();
                c = c.and(EMPLOYEE.EXPERIENCE.between(ex.getMin(), ex.getMax()));
            }
            if (search.getSex().isPresent()) {
                var s = search.getSex().get();
                c = c.and(EMPLOYEE.SEX.eq(typeSexMapper.toJooq(s.getSex())));
            }
            if (search.getAge().isPresent()) {
                var a = search.getAge().get();
                c = c.and(DSL.condition("EXTRACT(YEAR FROM AGE({0})) between {1} and {2}", EMPLOYEE.BIRTHDAY, a.getMin(), a.getMax()));
            }
            if (search.getChildren().isPresent()) {
                var ch = search.getChildren().get();
                c = c.and(EMPLOYEE.CHILDREN.between(ch.getMin(), ch.getMax()));
            }
            if (search.getSalary().isPresent()) {
                var sal = search.getSalary().get();
                c = c.and(JOB.SALARY.add(EMPLOYEE.SALARY_BONUS).between(sal.getMin(), sal.getMax()));
            }
        }

        List<EmployeeDTO> data = dsl.select(EMPLOYEE.fields())
                .from(EMPLOYEE)
                .join(JOB).on(EMPLOYEE.JOB.eq(JOB.ID))
                .where(c)
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
        return new ListResult<>(data, data.size());
    }

    /** Task 2 */
    public BrigadeEmployeesDTO brigadeEmployees(Integer brigadeId, Integer departmentId, Integer trainId) {
        Condition c = DSL.trueCondition();
        if (brigadeId != null) {
            c = c.and(BRIGADE_EMPLOYEES.BRIGADE.eq(brigadeId));
        }
        if (departmentId != null) {
            c = c.and(BRIGADE.DEPARTMENT.eq(departmentId));
        }
        if (trainId != null) {
            c = c.and(BRIGADE.ID.eq(TRAIN.DRIVER_BRIGADE).or(BRIGADE.ID.eq(TRAIN.SERVICE_BRIGADE))
                    .and(TRAIN.ID.eq(trainId)));
        }
        var employees = dsl.select(EMPLOYEE.fields())
                .from(BRIGADE_EMPLOYEES)
                .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                .join(BRIGADE).on(BRIGADE_EMPLOYEES.BRIGADE.eq(BRIGADE.ID))
                .join(JOB).on(EMPLOYEE.JOB.eq(JOB.ID))
                .join(TRAIN).on(BRIGADE.ID.in(TRAIN.DRIVER_BRIGADE, TRAIN.SERVICE_BRIGADE))
                .where(c)
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
        Float avgSalary = dsl.select(DSL.avg(JOB.SALARY.add(EMPLOYEE.SALARY_BONUS)))
                .from(BRIGADE_EMPLOYEES)
                .join(EMPLOYEE).on(BRIGADE_EMPLOYEES.EMPLOYEE.eq(EMPLOYEE.ID))
                .join(JOB).on(EMPLOYEE.JOB.eq(JOB.ID))
                .where(c)
                .fetchOne(0, Float.class);
        if (avgSalary == null) avgSalary = 0f;
        return new BrigadeEmployeesDTO(employees, employees.size(), avgSalary);
    }

    /** Task 3 */
    public ListResult<EmployeeDTO> medicalExams(MedicalExamSearchDTO search) {
        Condition c = MEDICAL_EXAM.CREATED_AT.year().eq(search.getYear())
                .and(MEDICAL_EXAM.VERDICT_GOOD.eq(search.getVerdict()));
        List<EmployeeDTO> data = dsl.select(EMPLOYEE.fields())
                .from(MEDICAL_EXAM)
                .join(EMPLOYEE).on(MEDICAL_EXAM.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(c)
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
        return new ListResult<>(data, data.size());
    }

    /** Task 4 */
    public ListResult<TrainDTO> trainsAtStation(Integer station, Date at) {
        Condition c = TRAIN_SERVICE.STATION.eq(station);
        if (at != null) {
            c = c.and(TRAIN_SERVICE.DEPARTURE_AT.le(at)).and(TRAIN_SERVICE.ARRIVAL_AT.ge(at));
        }
        List<TrainDTO> data = dsl.select(TRAIN.fields())
                .from(TRAIN_SERVICE)
                .join(TRAIN).on(TRAIN_SERVICE.TRAIN.eq(TRAIN.ID))
                .where(c)
                .fetch()
                .map(r -> trainMapper.toDto(r.into(TRAIN)));
        return new ListResult<>(data, data.size());
    }

    /** Task 5 */
    public ListResult<TrainDTO> inspectedTrains(Date from, Date to) {
        Condition c = INSPECTION.INSPECTED_AT.between(from, to);
        List<TrainDTO> data = dsl.select(TRAIN.fields())
                .from(INSPECTION)
                .join(TRAIN).on(INSPECTION.TRAIN.eq(TRAIN.ID))
                .where(c)
                .fetch()
                .map(r -> trainMapper.toDto(r.into(TRAIN)));
        return new ListResult<>(data, data.size());
    }

    /** Task 6 */
    public ListResult<TrainServiceDTO> trainsByRoute(Integer route) {
        Condition c = TRAIN_SERVICE.ROUTE.eq(route);
        List<TrainServiceDTO> data = dsl.select(TRAIN_SERVICE.fields())
                .from(TRAIN_SERVICE)
                .where(c)
                .fetch()
                .map(r -> trainServiceMapper.toDto(r.into(TRAIN_SERVICE)));
        return new ListResult<>(data, data.size());
    }

    /** Task 7 */
    public ListResult<TrainServiceDTO> canceledServices(Integer route) {
        Condition c = TRAIN_SERVICE.STATUS.eq(com.mykola.railroad.db.public_.enums.TypeTrainSt.CANCELED);
        if (route != null) {
            c = c.and(TRAIN_SERVICE.ROUTE.eq(route));
        }
        List<TrainServiceDTO> data = dsl.selectFrom(TRAIN_SERVICE).where(c)
                .fetch()
                .map(trainServiceMapper::toDto);
        return new ListResult<>(data, data.size());
    }

    /** Task 8 */
    public ListResult<DelayServiceDTO> delayedServices(Integer route, com.mykola.railroad.db.public_.enums.TypeDelay type) {
        Condition c = DELAY.DELAY_TYPE.eq(type);
        if (route != null) {
            c = c.and(DELAY.DELAY_SERVICE.in(DSL.select(TRAIN_SERVICE.ID).from(TRAIN_SERVICE).where(TRAIN_SERVICE.ROUTE.eq(route))));
        }
        List<DelayServiceDTO> data = dsl.select(DELAY.fields())
                .select(TRAIN_SERVICE.fields())
                .from(DELAY)
                .join(TRAIN_SERVICE).on(DELAY.DELAY_SERVICE.eq(TRAIN_SERVICE.ID))
                .where(c)
                .fetch()
                .map(r -> new DelayServiceDTO(delayMapper.toDto(r.into(DELAY)), trainServiceMapper.toDto(r.into(TRAIN_SERVICE))));
        return new ListResult<>(data, data.size());
    }

    /** Task 9 */
    public AvgSoldTicketDTO avgSoldTickets(Date from, Date to, Integer route) {
        Condition c = TICKET.SOLD_AT.between(from, to);
        if (route != null) {
            c = c.and(TICKET.TRAIN_SERVICE.in(DSL.select(TRAIN_SERVICE.ID).from(TRAIN_SERVICE).where(TRAIN_SERVICE.ROUTE.eq(route))));
        }
        Float avg = dsl.select(DSL.avg(DSL.count()))
                .from(TICKET)
                .where(c)
                .fetchOne(0, Float.class);
        return new AvgSoldTicketDTO(avg == null ? 0f : avg);
    }

    /** Task 10 */
    public ListResult<RouteDTO> routesByCategory(boolean international) {
        List<RouteDTO> data = dsl.selectFrom(ROUTE)
                .where(ROUTE.INTERNATIONAL.eq(international))
                .fetch()
                .map(r -> routeMapper.toDto(r));
        return new ListResult<>(data, data.size());
    }

    /** Task 11 */
    public ListResult<CustomerDTO> passengers(Integer serviceId) {
        List<CustomerDTO> data = dsl.select(CUSTOMER.fields())
                .from(TICKET)
                .join(CUSTOMER).on(TICKET.CUSTOMER.eq(CUSTOMER.ID))
                .where(TICKET.TRAIN_SERVICE.eq(serviceId))
                .fetch()
                .map(r -> customerMapper.toDto(r.into(CUSTOMER)));
        return new ListResult<>(data, data.size());
    }

    /** Task 12 */
    public ListResult<TicketDTO> unclaimedTickets(Integer serviceId) {
        List<TicketDTO> data = dsl.selectFrom(TICKET)
                .where(TICKET.TRAIN_SERVICE.eq(serviceId).and(TICKET.STATUS.eq(com.mykola.railroad.db.public_.enums.TypeTicketSt.CANCELED)))
                .fetch()
                .map(ticketMapper::toDto);
        return new ListResult<>(data, data.size());
    }

    /** Task 13 */
    public Integer returnedTickets(Integer serviceId) {
        return dsl.fetchCount(TICKET, TICKET.TRAIN_SERVICE.eq(serviceId).and(TICKET.STATUS.eq(com.mykola.railroad.db.public_.enums.TypeTicketSt.CANCELED)));
    }
}
