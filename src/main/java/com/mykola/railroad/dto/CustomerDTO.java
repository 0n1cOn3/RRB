package com.mykola.railroad.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class CustomerDTO {
    public Integer id;
    public String firstName;
    public String lastName;
    public TypeSex sex;
    public Short age;
    public Date createdAt;
    public Time createdWhen;
}
