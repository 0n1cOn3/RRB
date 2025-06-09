INSERT INTO job (id, short_title, title, salary) VALUES
(1,'CEO', 'Chief Executive Officer',9999999),
(2,'Driver', 'Locomotive Driver', 4000),
(3,'Dispatcher', 'Station Dispatcher', 3000),
(4,'Technician', 'Repair Technician', 3500),
(5,'Cashier', 'Ticket Cashier', 2500),
(6,'Manager', 'Department Manager', 5000),
(7,'Cleaner', 'Train Cleaner', 2000),
(8,'Caterer', 'Train Caterer', 2300),
(9,'Inspector', 'Train Inspector', 3000),
(10,'Engineer', 'Railway Engineer', 4500),
(11,'Security', 'Railway Security', 2800),
(12,'Electrician', 'Railway Electrician', 3200),
(13,'Planner', 'Route Planner', 4000),
(14,'Analyst', 'Traffic Analyst', 3800);

-- SELECT crypt('1', gen_salt('bf', 10));
-- $2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW

INSERT INTO employee (id,first_name, last_name, email, password, sex, children, birthday, job, experience, salary_bonus) VALUES
(1,'Mykola','Admin','admin@railroad.org',         '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 0, '2002-12-23', 2, 999, 9999999), -- sysop
(2,'John','Doe', 'john.doe@railroad.org',         '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 1, '2001-07-20', 2, 4, 200),     -- driver
(3,'Jane', 'Smith','jane.smith@railroad.org',     '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 2,'1989-12-22', 2, 1, 300),   -- driver + manager
(4,'Mike', 'Brown', 'mike.brown@railroad.org',    '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 1,'1991-02-11', 3, 2, 100),     -- repairmen
(5,'Alice', 'Davis', 'alice.davis@railroad.org',  '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 0,'1996-06-17', 4, 0, 150),   -- finance
(6,'Bob', 'Wilson', 'bob.wilson@railroad.org',    '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 0,'1987-09-10', 5, 0, 0),       -- manager
(7,'Eve', 'Taylor', 'eve.taylor@railroad.org',    '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 1,'1992-10-13', 6, 1, 500),   -- repairmen
(8,'Tom', 'Harris', 'tom.harris@railroad.org',    '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 1, '2001-12-24',7, 5, 50),      -- repairmen
(9,'Laura', 'Clark', 'laura.clark@railroad.org',  '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 1, '1983-01-05',8, 5, 100),   -- repairmen
(10,'Steve', 'Adams', 'steve.adams@railroad.org', '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 2, '1998-03-18',9, 2, 200),     -- repairmen
(11,'Nina', 'Morris', 'nina.morris@railroad.org', '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 1, '1990-12-14',10, 0, 300),  -- repairmen
(12,'James', 'King', 'james.king@railroad.org',   '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 0, '1999-02-05',11, 5, 50),     -- driver + manager
(13,'Sophia', 'Lee', 'sophia.lee@railroad.org',   '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 0, '1994-10-03',12, 3, 150),  -- driver + manager
(14,'George', 'Hall', 'george.hall@railroad.org', '$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 0, '1994-10-07',13, 9, 100);    -- driver + manager + finance

INSERT INTO employee_acl (employee, acl) VALUES
(1,'sysop'),        -- Mykola, sysop
(2, 'driver'),      -- John Doe, driver
(3, 'driver'),      -- Jane Smith, driver
(3, 'manager'),     -- Jane Smith, manager
(4, 'repairmen'),   -- Mike Brown, repairmen
(5, 'finance'),     -- Alice Davis, finance
(6, 'manager'),     -- Bob Wilson, manager
(7, 'repairmen'),   -- Eve Taylor, repairmen
(8, 'repairmen'),   -- Tom Harris, repairmen
(9, 'repairmen'),   -- Laura Clark, repairmen
(10, 'repairmen'),  -- Steve Adams, repairmen
(11, 'repairmen'),  -- Nina Morris, repairmen
(12, 'driver'),     -- James King, driver
(12, 'manager'),    -- James King, manager
(13, 'driver'),     -- Sophia Lee, driver
(13, 'manager'),    -- Sophia Lee, manager
(14, 'driver'),     -- George Hall, driver
(14, 'manager'),    -- George Hall, manager
(14, 'finance');    -- George Hall, finance

INSERT INTO department (short_title, title) VALUES
('Admin', 'Administration Department'),
('Tech', 'Technical Department'),
('Ops', 'Operations Department'),
('Clean', 'Cleaning Department'),
('Cater', 'Catering Department'),
('Inspect', 'Inspection Department'),
('Eng', 'Engineering Department'),
('Sec', 'Security Department'),
('Elect', 'Electrical Department'),
('Plan', 'Planning Department');

INSERT INTO department_employee (employee, department, head) VALUES
(6, 1, TRUE),
(1, 2, FALSE),
(2, 2, FALSE),
(3, 3, TRUE),
(4, 3, FALSE),
(5, 3, FALSE),
(7, 4, TRUE),
(8, 5, TRUE),
(9, 6, TRUE),
(10, 7, TRUE),
(11, 8, TRUE),
(12, 9, TRUE),
(13, 10, TRUE);

INSERT INTO brigade (department, brigadier) VALUES
(2, 1),
(2, 2),
(3, 3),
(4, 7),
(5, 8),
(6, 9),
(7, 10),
(8, 11),
(9, 12),
(10, 13);

INSERT INTO brigade_employees (brigade, employee) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 5),
(4, 7),
(5, 8),
(6, 9),
(7, 10),
(8, 11),
(9, 12),
(10, 13);

INSERT INTO train (train_type, in_service, driver_brigade, service_brigade, installed) VALUES
('passenger', TRUE, 1, 3, '2022-01-01'),
('cargo', TRUE, 1, 3, '2021-06-15'),
('special', FALSE, 2, 3, '2023-03-20'),
('passenger', TRUE, 1, 3, '2022-02-15'),
('cargo', FALSE, 2, 4, '2020-11-10'),
('special', TRUE, 3, 5, '2023-05-01'),
('passenger', TRUE, 3, 6, '2022-03-05'),
('cargo', TRUE, 4, 7, '2021-08-25'),
('special', FALSE, 5, 8, '2023-02-12'),
('passenger', TRUE, 6, 9, '2023-04-18');

INSERT INTO inspection (train, type, inspected_at, status, description) VALUES
(1, 'planned', '2023-05-22', TRUE, 'Good');

INSERT INTO station (name) VALUES
('Central Station'),
('North Station'),
('East Station'),
('West Station'),
('South Station'),
('Airport Terminal'),
('Harbor Junction'),
('City Suburbs'),
('Mountain Pass'),
('Desert Plains');

INSERT INTO route (name, international) VALUES
('City Express', FALSE),
('International Express', TRUE),
('Tourist Special', TRUE),
('Harbor Line', FALSE),
('Mountain Adventure', TRUE),
('Desert Journey', FALSE),
('Airport Shuttle', FALSE),
('Northern Lights', TRUE),
('Southern Comfort', FALSE),
('Urban Explorer', FALSE);

INSERT INTO route_station (route, station, ordered) VALUES
(1, 1, 1),
(1, 2, 2),
(2, 1, 1),
(2, 3, 2),
(3, 4, 1),
(3, 5, 2),
(4, 6, 1),
(4, 7, 2),
(5, 8, 1),
(5, 9, 2),
(6, 10, 1),
(7, 1, 1),
(7, 6, 2),
(8, 2, 1),
(8, 3, 2);

INSERT INTO train_service (train, route, departure_at, departure_when, arrival_at, arrival_when, status, driver, station, next_station_at) VALUES
(1, 1, '2025-05-22', '08:00', '2025-05-23', '12:00', 'ok', 1, 1, '12:00'),
(2, 2, '2025-05-23', '09:30', '2025-05-24', '15:00', 'ok', 2, 1, '15:00'),
(3, 3, '2025-05-24', '07:00', '2025-05-25', '11:00', 'ok', 3, 4, '11:00'),
(4, 4, '2025-05-25', '14:00', '2025-05-26', '18:00', 'ok', 4, 6, '15:30'),
(5, 5, '2025-05-26', '10:00', '2025-05-27', '15:00', 'ok', 5, 8, '13:00'),
(6, 6, '2025-05-27', '06:00', '2025-05-28', '12:00', 'ok', 6, 10, '12:00'),
(7, 7, '2025-05-28', '09:00', '2025-05-29', '13:00', 'canceled', 7, 1, '10:55'),
(8, 8, '2025-05-29', '15:00', '2025-05-30', '19:00', 'ok', 8, 3, '19:00'),
(9, 8, '2025-05-30', '08:30', '2025-05-31', '13:30', 'ok', 9, 2, '13:00'),
(10, 4, '2025-05-30', '10:30', '2025-05-31', '14:30', 'ok', 10, 7, '14:30');

-- SELECT crypt('1', gen_salt('bf', 10));
-- $2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW

INSERT INTO customer (id, first_name, last_name, email, password, sex, age) VALUES
(1,'Harry', 'Taylor',       'harry.taylor@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 40),
(2,'Olivia', 'Martinez',    'olivia.martinez@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 27),
(3,'Ethan', 'Garcia',       'ethan.garcia@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 29),
(4,'Sophia', 'Robinson',    'sophia.robinson@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 33),
(5,'Liam', 'Clark',         'liam.clark@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 21),
(6,'Isabella', 'Rodriguez', 'isabella.rodriguez@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 30),
(7,'Noah', 'Lewis',         'noah.lewis@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 26),
(8,'Ava', 'Walker',         'ava.walker@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 24),
(9,'Mason', 'Young',        'mason.young@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','male', 39),
(10,'Ella', 'Hill',         'ella.hill@contoso.com','$2a$10$GcG2dHyWvdVqrkQfLdZateIJ/Ytts1BpK/8Vk01B3515pIrslqhbW','female', 35);

INSERT INTO ticket (customer, train_service, seat, cost, sold_at, passenger, status) VALUES
(1, 1, 'A1', 500, '2025-04-08', 'regular', 'ok'),
(2, 2, 'B2', 800, '2025-04-10', 'vip', 'ok'),
(3, 3, 'C3', 500,'2025-04-14', 'regular', 'ok'),
(4, 4, 'D4', 550,'2025-04-17', 'regular', 'ok'),
(5, 5, 'E5', 800,'2025-04-18', 'vip', 'ok'),
(6, 6, 'F6', 100,'2025-04-24', 'regular', 'ok'),
(7, 7, 'G7', 200,'2025-04-25', 'regular', 'ok'),
(8, 8, 'H8', 1000,'2025-04-29', 'vip', 'ok'),
(9, 9, 'I9', 700,'2025-04-30', 'regular', 'ok'),
(1, 10, 'J10', 500,'2025-05-05', 'regular', 'ok'),
(2, 1, 'K11', 500,'2025-05-06', 'regular', 'ok'),
(2, 2, 'L12', 700,'2025-05-14', 'vip', 'ok'),
(3, 3, 'M13', 400,'2025-05-15', 'regular', 'ok'),
(4, 4, 'N14', 400,'2025-05-20', 'regular', 'ok'),
(5, 5, 'O15', 700,'2025-05-21', 'vip', 'ok'),
(6, 6, 'P16', 400,'2025-05-26', 'regular', 'ok'),
(7, 7, 'Q17', 400,'2025-05-27', 'regular', 'canceled'),
(8, 8, 'R18', 900,'2025-06-02', 'vip', 'ok'),
(9, 9, 'S19', 450,'2025-06-04', 'regular', 'ok');

INSERT INTO delay (created_at, created_when, delay_until, delay_type, delay_service, cancel_service) VALUES
('2025-05-22', '08:00', '08:10', 'service', 1, FALSE),
('2025-05-23', '09:30', '09:45', 'legal', 2, TRUE),
('2025-05-24', '07:00', '07:05', 'service', 3, FALSE),
('2025-05-25', '14:00', '14:20', 'service', 4, FALSE),
('2025-05-26', '10:00', '10:12', 'legal', 5, TRUE),
('2025-05-27', '06:00', '06:30', 'service', 6, FALSE),
('2025-05-28', '09:00', '09:08', 'legal', 7, TRUE),
('2025-05-29', '15:00', '15:25', 'service', 8, FALSE),
('2025-05-30', '08:30', '08:37', 'service', 9, FALSE),
('2025-05-31', '10:30', '10:48', 'legal', 10, TRUE);

INSERT INTO baggage (ticket, weight, status) VALUES
(1, 20.0, 'ok'),
(2, 25.5, 'customs'),
(3, 15.2, 'ok'),
(4, 22.0, 'ok'),
(5, 18.8, 'failed'),
(6, 30.3, 'detention'),
(7, 10.0, 'ok'),
(8, 28.1, 'ok'),
(9, 16.4, 'customs'),
(11, 35.0, 'ok'),
(10, 12.0, 'failed'),
(12, 25.0, 'ok'),
(13, 20.0, 'customs'),
(14, 18.0, 'detention'),
(15, 40.0, 'ok'),
(16, 10.5, 'failed'),
(17, 22.3, 'ok'),
(18, 33.0, 'ok'),
(19, 15.0, 'customs');
