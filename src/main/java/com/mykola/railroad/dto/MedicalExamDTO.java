package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MedicalExamDTO {
    public Integer id;
    public Integer employee;
    public Date createdAt;
    public String verdict;
    public Boolean verdictGood;
}
