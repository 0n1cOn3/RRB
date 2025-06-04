package com.mykola.railroad.controller;

import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.dto.EmployeeSearchDTO;
import com.mykola.railroad.dto.TypeACL;
import com.mykola.railroad.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}/acl")
    public List<TypeACL> getEmployeeACLs(@PathVariable("id") Integer employeeId) {
        return employeeService.getEmployeeACLs(employeeId);
    }

    @PostMapping("/search")
    public List<EmployeeDTO> searchEmployees(@RequestBody @Valid EmployeeSearchDTO search) {
        return employeeService.search(search);
    }

    // TODO: test endpoint
    @GetMapping("/email/{email}")
    public EmployeeDTO getEmployeeByEmail(@PathVariable("email") String email) {
        return employeeService.findEmployeeByEmail(email)
                .orElseThrow();
    }

    @GetMapping
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.findAllEmployees();
    }
}
