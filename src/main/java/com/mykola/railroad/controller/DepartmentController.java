package com.mykola.railroad.controller;

import com.mykola.railroad.dto.EmployeeDTO;
import com.mykola.railroad.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{id}/employee")
    public List<EmployeeDTO> getDepartmentEmployees(@PathVariable("id") Integer id) {
        return departmentService.getDepartmentEmployees(id);
    }
}
