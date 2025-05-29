package com.mykola.railroad.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MedicalExamDTO {
    public Integer id;
    public Integer employee;
    public Date createdAt;
    public String verdict;
    public Boolean verdictGood;
}
