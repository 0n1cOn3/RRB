CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TYPE type_acl AS ENUM(
    'customer',
    'driver',
    'repairmen',
    'manager',
    'finance',
    'sysop'
);

-- job & employee

CREATE TYPE type_sex AS ENUM ('male', 'female');

CREATE TABLE job (
    id              SERIAL          PRIMARY KEY,
    short_title     VARCHAR         NOT NULL,
    title           VARCHAR         NOT NULL,
    salary          DECIMAL         NOT NULL
);
CREATE UNIQUE INDEX idx_job_short_title ON job(short_title);
CREATE INDEX idx_job_salary ON job(salary);

CREATE TABLE employee (
    id              SERIAL          PRIMARY KEY,

    email           VARCHAR         NOT NULL UNIQUE,
    password        VARCHAR         NOT NULL,

    first_name      VARCHAR         NOT NULL,
    last_name       VARCHAR         NOT NULL,

    sex             type_sex        NOT NULL,
    children        SMALLINT        NOT NULL,

    birthday        DATE            NOT NULL,

    -- job experience before this employment, to get current experience
    -- subtract employed_at from CURRENT_DATE and add it to experience, round to 1
    experience      SMALLINT        NOT NULL,
    employed_at     DATE            NOT NULL DEFAULT CURRENT_DATE,

    job             INTEGER         REFERENCES job(id) NOT NULL,
    salary_bonus    DECIMAL         DEFAULT(0)
);

CREATE TABLE employee_acl (
    id              SERIAL          NOT NULL,
    employee        INTEGER         NOT NULL REFERENCES employee(id),
    acl             type_acl        NOT NULL,

    PRIMARY KEY(id, employee, acl)
);

-- department

CREATE TABLE department (
    id              SERIAL          PRIMARY KEY,
    short_title     VARCHAR         NOT NULL,
    title           VARCHAR         NOT NULL
);
CREATE UNIQUE INDEX idx_department_stitle ON department(short_title);

CREATE TABLE department_employee (
    employee        INTEGER         REFERENCES employee(id) NOT NULL,
    department      INTEGER         REFERENCES department(id) NOT NULL,
    head            BOOLEAN         NOT NULL DEFAULT('FALSE'),

    -- primary key, but also ensures 1 employee 1 department
    PRIMARY KEY(employee, department)
);

CREATE FUNCTION fn_chk_department_head()
    RETURNS trigger AS
    $func$
BEGIN
    IF  (NEW.head = TRUE) AND (SELECT COUNT(head)
        FROM department_employee
        WHERE department = NEW.department AND head = TRUE) > 0
    THEN
        RAISE EXCEPTION 'Department already has Head';
END IF;
RETURN NEW;
END
$func$ LANGUAGE plpgsql;

CREATE TRIGGER trg_department_head
    BEFORE INSERT ON department_employee
    FOR EACH ROW EXECUTE PROCEDURE fn_chk_department_head();

-- medical exams for employees
CREATE TABLE medical_exam (
    id              SERIAL          PRIMARY KEY,
    employee        INTEGER         REFERENCES employee(id) NOT NULL,
    created_at      DATE            NOT NULL,
    verdict         VARCHAR,
    verdict_good    BOOLEAN         NOT NULL
);

-- brigade
CREATE TABLE brigade (
    id              SERIAL          PRIMARY KEY,
    department      INTEGER         REFERENCES department(id) NOT NULL,
    brigadier       INTEGER         REFERENCES employee(id) NOT NULL
);

CREATE TABLE brigade_employees (
    brigade         INTEGER         REFERENCES brigade(id) NOT NULL,
    employee        INTEGER         REFERENCES employee(id) NOT NULL,

    PRIMARY KEY (brigade, employee)
);

-- train
CREATE TYPE type_train AS ENUM ('passenger', 'cargo', 'special');

CREATE TABLE train (
    id              SERIAL          PRIMARY KEY,
    train_type      type_train      NOT NULL DEFAULT('passenger'),
    in_service      BOOLEAN         NOT NULL DEFAULT('true'),
    driver_brigade  INTEGER         REFERENCES brigade(id) NOT NULL,
    service_brigade INTEGER         REFERENCES brigade(id) NOT NULL,
    installed       DATE            NOT NULL DEFAULT CURRENT_DATE
);
-- driver_brigade, service_brigade - out of these brigades we will pick man
-- for each train run

-- inspection
CREATE TYPE type_inspection AS ENUM('planned', 'run');

CREATE TABLE inspection (
    id              SERIAL          PRIMARY KEY,
    train           INTEGER         REFERENCES train(id),
    type            type_inspection NOT NULL DEFAULT('run'),
    inspected_at    DATE            NOT NULL DEFAULT CURRENT_DATE,
    status          BOOLEAN         NOT NULL,
    description     TEXT
);

-- station & route
CREATE TABLE station (
    id              SERIAL          PRIMARY KEY,
    name            VARCHAR         NOT NULL
);

CREATE TABLE route (
    id              SERIAL          PRIMARY KEY,
    name            VARCHAR         NOT NULL,
    international   BOOLEAN         NOT NULL DEFAULT('false')
);

CREATE TABLE route_station (
    route           INTEGER         REFERENCES route(id) NOT NULL,
    station         INTEGER         REFERENCES station(id) NOT NULL,
    ordered         INTEGER         NOT NULL,

    PRIMARY KEY(route, station)
);
CREATE INDEX idx_route_station_order ON route_station(ordered);

-- train service (run)
CREATE TYPE type_train_st AS ENUM('ok', 'completed', 'delayed', 'canceled');

CREATE TABLE train_service (
    id              SERIAL          PRIMARY KEY,
    train           INTEGER         REFERENCES train(id) NOT NULL,
    route           INTEGER         REFERENCES route(id) NOT NULL,
    departure_at    DATE            NOT NULL,
    departure_when  TIME            NOT NULL,
    arrival_at      DATE            NOT NULL,
    arrival_when    TIME            NOT NULL,
    status          type_train_st   NOT NULL DEFAULT('ok'),
    driver          INTEGER         REFERENCES employee(id) NOT NULL,

    station         INTEGER         NOT NULL,
    next_station_at  TIME           NOT NULL,

    FOREIGN KEY (route, station) REFERENCES route_station(route, station)
);

-- passengers (customers)
CREATE TABLE customer (
    id              SERIAL          PRIMARY KEY,

    email           VARCHAR         NOT NULL UNIQUE,
    password        VARCHAR         NOT NULL,
    -- ACL is always customer for a customer

    first_name      VARCHAR         NOT NULL,
    last_name       VARCHAR         NOT NULL,

    sex             type_sex        NOT NULL,
    age             SMALLINT        NOT NULL,

    created_at      DATE            NOT NULL DEFAULT CURRENT_DATE,
    created_when    TIME            NOT NULL DEFAULT CURRENT_TIME
);

CREATE TYPE type_ticket_st AS ENUM('ok', 'canceled');
CREATE TYPE type_passenger AS ENUM('regular', 'business', 'vip');
CREATE TYPE type_seat AS (
    seat_x          SMALLINT,
    seat_y          SMALLINT
    );

CREATE TABLE ticket (
    id              SERIAL          PRIMARY KEY,
    customer        INTEGER         REFERENCES customer(id) NOT NULL,
    train_service   INTEGER         REFERENCES train_service(id) NOT NULL,
    seat            VARCHAR         NOT NULL,
    passenger       type_passenger  NOT NULL DEFAULT('regular'),
    status          type_ticket_st  NOT NULL DEFAULT('ok')
);
-- ensure 1 train service 1 customer 1 passenger 1 seat 1 type constraint
CREATE UNIQUE INDEX idx_unique_customer ON ticket(customer, train_service, seat, passenger);

CREATE TYPE type_baggage_st AS ENUM('customs', 'failed', 'detention', 'ok');

-- ensure 1 baggage per 1 ticket
CREATE TABLE baggage (
    ticket          INTEGER         REFERENCES ticket(id) PRIMARY KEY,
    weight          FLOAT           NOT NULL,
    status          type_baggage_st NOT NULL DEFAULT('customs')
);

CREATE TYPE type_delay AS ENUM('service', 'legal');

CREATE TABLE delay (
    id              SERIAL          PRIMARY KEY,
    created_at      DATE            NOT NULL DEFAULT CURRENT_DATE,
    created_when    TIME            NOT NULL DEFAULT CURRENT_TIME,
    delay_until     TIME            NOT NULL,
    delay_type      type_delay      NOT NULL DEFAULT('service'),
    delay_service   INTEGER         REFERENCES train_service(id) NOT NULL,
    cancel_service  BOOLEAN         NOT NULL DEFAULT('FALSE')
);
