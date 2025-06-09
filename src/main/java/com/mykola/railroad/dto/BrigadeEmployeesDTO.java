package com.mykola.railroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BrigadeEmployeesDTO {
    private List<EmployeeDTO> employees;
    private Integer total;
    private Float avgSalary;
}
