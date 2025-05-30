package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
    public Integer id;
    public String firstName;
    public String lastName;
    public TypeSex sex;
    public Integer job;
    public Float salaryBonus;
}