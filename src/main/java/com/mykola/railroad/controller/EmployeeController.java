package com.mykola.railroad.controller;

import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.findAllEmployees();
    }
}
