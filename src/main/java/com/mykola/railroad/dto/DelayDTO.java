package com.mykola.railroad.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class DelayDTO {
    public Integer id;
    public Date createdAt;
    public Time createdWhen;
    public Time delayUntil;
    public TypeDelay delayType;
    public Integer delayService;
    public Boolean cancelService;
}
