package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class CustomerDTO {
    public Integer id;
    private String email;
    private String password;
    public String firstName;
    public String lastName;
    public String sex;
    public Short age;
    public Date createdAt;
    public Time createdWhen;
}
