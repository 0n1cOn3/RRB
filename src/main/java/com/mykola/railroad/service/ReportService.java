package com.mykola.railroad.service;

import com.mykola.railroad.db.public_.enums.TypeTicketSt;
import com.mykola.railroad.db.public_.enums.TypeTrainSt;
import com.mykola.railroad.dto.*;
import com.mykola.railroad.mapper.*;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mykola.railroad.db.public_.Tables.*;
import static org.jooq.impl.DSL.*;

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

    private Condition whereEmployeeSearch(Condition c, EmployeeSearchDTO search) {
        if (search != null) {
            if (search.getExperience().isPresent()) {
                var ex = search.getExperience().get();
                c = c.and(EMPLOYEE.EXPERIENCE.between(ex.getMin().shortValue(), ex.getMax().shortValue()));
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
                c = c.and(EMPLOYEE.CHILDREN.between(ch.getMin().shortValue(), ch.getMax().shortValue()));
            }
            if (search.getSalary().isPresent()) {
                var sal = search.getSalary().get();
                c = c.and(JOB.SALARY.add(EMPLOYEE.SALARY_BONUS)
                        .between(new BigDecimal(sal.getMin()), new BigDecimal(sal.getMax())));
            }
        }

        return c;
    }

    private Condition whereCustomerSearch(Condition c, CustomerSearchDTO search) {
        if (search != null) {
            if (search.getSex().isPresent()) {
                var s = search.getSex().get();
                c = c.and(CUSTOMER.SEX.eq(typeSexMapper.toJooq(s.getSex())));
            }
            if (search.getAge().isPresent()) {
                var a = search.getAge().get();
                c = c.and(CUSTOMER.AGE.between(search.age.get().min.shortValue(), search.age.get().max.shortValue()));
            }
        }

        return c;
    }

    /** Task 1 */
    public ListResult<EmployeeDTO> employees(Integer departmentId, boolean headsOnly, EmployeeSearchDTO search) {
        Condition c = DSL.trueCondition();
        if (departmentId != null) {
            c = c.and(EMPLOYEE.ID.in(select(DEPARTMENT_EMPLOYEE.EMPLOYEE).from(DEPARTMENT_EMPLOYEE)
                    .where(DEPARTMENT_EMPLOYEE.DEPARTMENT.eq(departmentId))));
        }
        if (headsOnly) {
            c = c.and(EMPLOYEE.ID.in(select(DEPARTMENT_EMPLOYEE.EMPLOYEE).from(DEPARTMENT_EMPLOYEE)
                    .where(DEPARTMENT_EMPLOYEE.HEAD.isTrue())));
        }
        c = whereEmployeeSearch(c, search);

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
        Float avgSalary = dsl.select(avg(JOB.SALARY.add(EMPLOYEE.SALARY_BONUS)))
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
        Condition c = DSL.extract(MEDICAL_EXAM.CREATED_AT, DatePart.YEAR).eq(search.getYear())
                .and(MEDICAL_EXAM.VERDICT_GOOD.eq(search.getVerdict()));
        c = whereEmployeeSearch(c, search.employee);

        List<EmployeeDTO> data = dsl.select(EMPLOYEE.fields())
                .from(MEDICAL_EXAM)
                .join(EMPLOYEE).on(MEDICAL_EXAM.EMPLOYEE.eq(EMPLOYEE.ID))
                .where(c)
                .fetch()
                .map(r -> employeeMapper.toDto(r.into(EMPLOYEE)));
        return new ListResult<>(data, data.size());
    }

    /** Task 4 */
    public ListResult<TrainAndServiceCountDTO> trainsAtStation(Integer station, String atStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate at = LocalDate.parse(atStr, formatter);

        Condition c = TRAIN_SERVICE.STATION.eq(station);
        if (at != null) {
            c = c.and(TRAIN_SERVICE.DEPARTURE_AT.greaterOrEqual(at));
        }
        List<TrainAndServiceCountDTO> data = dsl.select(DSL.asterisk(),
                        select(count())
                                .from(TRAIN_SERVICE)
                                .where(TRAIN_SERVICE.TRAIN.eq(TRAIN.ID))
                                .asField("services")
                        )
                .from(TRAIN_SERVICE)
                .join(TRAIN).on(TRAIN_SERVICE.TRAIN.eq(TRAIN.ID))
                .where(c)
                .fetch()
                .map(r -> new TrainAndServiceCountDTO(
                        trainMapper.toDto(r.into(TRAIN)),
                        trainServiceMapper.toDto(r.into(TRAIN_SERVICE)),
                        r.get("services", Integer.class)
                ));
        return new ListResult<>(data, data.size());
    }

    /** Task 5 */
    public ListResult<TrainDTO> inspectedTrains(String fromStr, String toStr, Integer age) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(fromStr, formatter);
        LocalDate to = LocalDate.parse(toStr, formatter);

        Condition c = INSPECTION.INSPECTED_AT.between(from, to);
        if (age != null) {
            c = c.and(DSL.condition("EXTRACT(YEAR FROM AGE({0})) = {1}", TRAIN.INSTALLED, age));
        }

        List<TrainDTO> data = dsl.select(TRAIN.fields())
                .from(INSPECTION)
                .join(TRAIN).on(INSPECTION.TRAIN.eq(TRAIN.ID))
                .where(c)
                .fetch()
                .map(r -> trainMapper.toDto(r.into(TRAIN)));
        return new ListResult<>(data, data.size());
    }

    /** Task 5.1 */
    public Integer servicesBeforeTrainBroke(Integer train) {
        return dsl
                .select(count())
                .from(TRAIN_SERVICE)
                .where(TRAIN_SERVICE.TRAIN.eq(train).and(TRAIN_SERVICE.DEPARTURE_AT.le(
                        select(INSPECTION.INSPECTED_AT)
                                .from(INSPECTION)
                                .where(INSPECTION.TRAIN.eq(train).and(INSPECTION.STATUS.eq(false)))
                )))
                .fetchOne(0, Integer.class);
    }
    /** Task 5.2 */
    public Integer timesTrainRepaired(Integer train) {
        return dsl
                .select(count())
                .from(INSPECTION)
                .where(INSPECTION.TRAIN.eq(train).and(INSPECTION.STATUS.eq(false)))
                .fetchOne(0, Integer.class);
    }

    /** Task 6 */
    public ListResult<AggregateTrainInfoDTO> trainsByRoute(TrainSearchDTO search) {
        if (search == null) {
            search = new TrainSearchDTO();
        }

        Field<Integer> routeLength = DSL.selectCount()
                .from(ROUTE_STATION)
                .where(ROUTE_STATION.ROUTE.eq(TRAIN_SERVICE.ROUTE))
                .asField("route_length");

        Field<java.math.BigDecimal> avgCost = select(avg(TICKET.COST))
                .from(TICKET)
                .where(TICKET.TRAIN_SERVICE.eq(TRAIN_SERVICE.ID))
                .asField("ticket_cost");

        Condition c = DSL.trueCondition();
        if (search.getRoute() != null) {
            c = c.and(TRAIN_SERVICE.ROUTE.eq(search.getRoute()));
        }
        if (search.getLength().isPresent()) {
            var len = search.getLength().get();
            c = c.and(routeLength.between(len.getMin(), len.getMax()));
        }
        if (search.getCost().isPresent()) {
            var cost = search.getCost().get();
            c = c.and(avgCost.between(new BigDecimal(cost.getMin()), new BigDecimal(cost.getMax())));
        }

        var records = dsl.select(DSL.asterisk())
                .from(
                        select(DSL.asterisk(), routeLength, avgCost)
                        .from(TRAIN)
                        .join(TRAIN_SERVICE).on(TRAIN_SERVICE.TRAIN.eq(TRAIN.ID))
                )
                .where(c)
                .fetch();

        List<AggregateTrainInfoDTO> data = records.map(r -> new AggregateTrainInfoDTO(
                trainMapper.toDto(r.into(TRAIN)),
                trainServiceMapper.toDto(r.into(TRAIN_SERVICE)),
                r.get(routeLength),
                r.get(avgCost, java.math.BigDecimal.class) == null ? null : r.get(avgCost, java.math.BigDecimal.class).floatValue()
        ));
        return new ListResult<>(data, data.size());
    }

    /** Task 7 */
    public ListResult<TrainServiceDTO> canceledServices(Integer route) {
        Condition c = TRAIN_SERVICE.STATUS.eq(TypeTrainSt.canceled);
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
            c = c.and(DELAY.DELAY_SERVICE.in(select(TRAIN_SERVICE.ID).from(TRAIN_SERVICE).where(TRAIN_SERVICE.ROUTE.eq(route))));
        }
        List<DelayServiceDTO> data = dsl.select(DELAY.fields())
                .select(TRAIN_SERVICE.fields())
                .from(DELAY)
                .join(TRAIN_SERVICE).on(DELAY.DELAY_SERVICE.eq(TRAIN_SERVICE.ID))
                .join(TICKET).on(TICKET.TRAIN_SERVICE.eq(TRAIN_SERVICE.ID))
                .where(c)
                .fetch()
                .map(r -> new DelayServiceDTO(
                        delayMapper.toDto(r.into(DELAY)),
                        trainServiceMapper.toDto(r.into(TRAIN_SERVICE)),
                        ticketMapper.toDto(r.into(TICKET))
                ));
        return new ListResult<>(data, data.size());
    }

    /** Task 9 */
    public List<AggregateTicketInfoDTO> aggregateTicketInfo(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(fromDate, formatter);
        LocalDate to = LocalDate.parse(toDate, formatter);

        return dsl
                .select(
                        TRAIN_SERVICE.ROUTE,
                        avg(TICKET.COST).as("ticket_cost"),
                        count(TICKET.ID).as("ticket_count"),
                        select(count())
                                .from(ROUTE_STATION)
                                .where(ROUTE_STATION.ROUTE.eq(TRAIN_SERVICE.ROUTE))
                                .asField("route_length")
                )
                .from(TICKET)
                .leftJoin(TRAIN_SERVICE).on(TRAIN_SERVICE.ID.eq(TICKET.TRAIN_SERVICE))
                .where(TICKET.SOLD_AT.between(from, to))
                .groupBy(TRAIN_SERVICE.ROUTE, field("route_length"))
                .fetch()
                .map(r -> new AggregateTicketInfoDTO(
                        r.get(TRAIN_SERVICE.ROUTE, Integer.class),
                        r.get("ticket_count", Integer.class),
                        r.get("ticket_cost", Float.class),
                        r.get("route_length", Integer.class)
                ));
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
    public ListResult<CustomerDTO> passengers(Integer serviceId, CustomerSearchDTO search) {
        Condition c = TICKET.TRAIN_SERVICE.eq(serviceId);
        if (search != null) {
            c = whereCustomerSearch(c, search);
        }

        List<CustomerDTO> data = dsl.select(CUSTOMER.fields())
                .from(TICKET)
                .join(CUSTOMER).on(TICKET.CUSTOMER.eq(CUSTOMER.ID))
                .where(c)
                .fetch()
                .map(r -> customerMapper.toDto(r.into(CUSTOMER)));
        return new ListResult<>(data, data.size());
    }

    /** Task 12 */
    public ListResult<TicketDTO> unclaimedTickets(Integer serviceId) {
        List<TicketDTO> data = dsl.selectFrom(TICKET)
                .where(TICKET.TRAIN_SERVICE.eq(serviceId).and(TICKET.STATUS.eq(TypeTicketSt.canceled)))
                .fetch()
                .map(ticketMapper::toDto);
        return new ListResult<>(data, data.size());
    }

    /** Task 13 */
    public Integer returnedTickets(Integer serviceId) {
        return dsl.fetchCount(TICKET, TICKET.TRAIN_SERVICE.eq(serviceId).and(TICKET.STATUS.eq(TypeTicketSt.canceled)));
    }
}
