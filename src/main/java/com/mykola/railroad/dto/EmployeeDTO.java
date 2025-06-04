package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeDTO {
    public Integer id;
    public String firstName;
    public String lastName;
    public TypeSex sex;
    public Integer children;
    public Integer experience;
    public Date employedAt;
    public Integer job;
    public Float salaryBonus;
}