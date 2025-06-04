package com.mykola.railroad.controller;

import com.mykola.railroad.dto.AvgSalaryDTO;
import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.dto.EmployeeSearchDTO;
import com.mykola.railroad.service.BrigadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brigade")
public class BrigadeController {
    @Autowired
    private BrigadeService brigadeService;

    @GetMapping("/{brigade}/department/{department}/employee")
    public List<EmployeeDTO> getBrigadeEmployeesInDepartment(@PathVariable Integer brigade, @PathVariable Integer department) {
        return brigadeService.getBrigadeEmployeesInDepartment(brigade, department);
    }

    @GetMapping("/train/{train}/employee")
    public List<EmployeeDTO> getTrainServiceBrigadeEmployees(@PathVariable Integer train) {
        return brigadeService.getTrainServiceBrigadeEmployees(train);
    }

    @PostMapping("/{id}/age/employee")
    public List<EmployeeDTO> searchBrigadeEmployeesByAge(@PathVariable Integer id,
                                                         @RequestBody @Valid EmployeeSearchDTO search) {
        return brigadeService.searchBrigadeEmployeesByAge(id, search);
    }

    @GetMapping("/{id}/salary")
    public AvgSalaryDTO getAvgBrigadeSalary(@PathVariable Integer id) {
        return brigadeService.getAvgBrigadeSalary(id);
    }

    @GetMapping("/{id}/employee")
    public List<EmployeeDTO> getBrigadeEmployees(@PathVariable Integer id) {
        return brigadeService.getBrigadeEmployees(id);
    }
}
