package com.mykola.railroad.controller;

import com.mykola.railroad.dto.*;
import com.mykola.railroad.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/employees")
    public ListResult<EmployeeDTO> employees(@RequestParam(required = false) Integer department,
                                             @RequestParam(defaultValue = "false") boolean heads,
                                             @RequestBody(required = false) EmployeeSearchDTO search) {
        return reportService.employees(department, heads, search);
    }

    @GetMapping("/brigade")
    public BrigadeEmployeesDTO brigadeEmployees(@RequestParam(required = false) Integer brigade,
                                                @RequestParam(required = false) Integer department,
                                                @RequestParam(required = false) Integer train) {
        return reportService.brigadeEmployees(brigade, department, train);
    }

    @PostMapping("/medical")
    public ListResult<EmployeeDTO> medical(@RequestBody MedicalExamSearchDTO search) {
        return reportService.medicalExams(search);
    }

    @GetMapping("/station/trains")
    public ListResult<TrainDTO> trainsAtStation(@RequestParam Integer station,
                                                @RequestParam(required = false) String at) {
        return reportService.trainsAtStation(station, at);
    }

    @GetMapping("/inspection")
    public ListResult<TrainDTO> inspected(@RequestParam String from, @RequestParam String to) {
        return reportService.inspectedTrains(from, to);
    }

    @GetMapping("/route/trains")
    public ListResult<TrainServiceDTO> trainsByRoute(@RequestParam Integer route) {
        return reportService.trainsByRoute(route);
    }

    @GetMapping("/canceled")
    public ListResult<TrainServiceDTO> canceled(@RequestParam(required = false) Integer route) {
        return reportService.canceledServices(route);
    }

    @GetMapping("/delayed")
    public ListResult<DelayServiceDTO> delayed(@RequestParam(required = false) Integer route,
                                               @RequestParam com.mykola.railroad.db.public_.enums.TypeDelay type) {
        return reportService.delayedServices(route, type);
    }

    @GetMapping("/avg-sold")
    public AvgSoldTicketDTO avgSold(@RequestParam String from, @RequestParam String to,
                                    @RequestParam(required = false) Integer route) {
        return reportService.avgSoldTickets(from, to, route);
    }

    @GetMapping("/routes")
    public ListResult<RouteDTO> routes(@RequestParam boolean international) {
        return reportService.routesByCategory(international);
    }

    @GetMapping("/passengers")
    public ListResult<CustomerDTO> passengers(@RequestParam Integer service) {
        return reportService.passengers(service);
    }

    @GetMapping("/unclaimed")
    public ListResult<TicketDTO> unclaimed(@RequestParam Integer service) {
        return reportService.unclaimedTickets(service);
    }

    @GetMapping("/returned")
    public Integer returned(@RequestParam Integer service) {
        return reportService.returnedTickets(service);
    }
}
