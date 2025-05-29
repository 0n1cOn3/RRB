package com.mykola.railroad.dto;

import java.math.BigDecimal;

public record EmployeeDTO(
        Integer id,
        String email,
        String password,
        String firstName,
        String lastName,
        String sex,
        Integer job,
        BigDecimal salaryBonus
) {}