package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class DelayDTO {
    public Integer id;
    public Date createdAt;
    public Time createdWhen;
    public Time delayUntil;
    public Integer delayService;
    public Boolean cancelService;
}
