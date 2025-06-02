package com.mykola.railroad.controller;

import com.mykola.railroad.dto.LoginDTO;
import com.mykola.railroad.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public void login(@Valid @RequestBody LoginDTO login, HttpServletRequest req, HttpServletResponse res) {
        employeeService.login(login, req, res);
    }

    @DeleteMapping("/employee")
    public void logout(HttpServletRequest req) throws ServletException {
        employeeService.logout(req);
    }
}
