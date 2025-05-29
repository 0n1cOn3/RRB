package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class TrainServiceDTO {
    public Integer id;
    public Integer train;
    public Integer route;
    public Date departureAt;
    public Time departureWhen;
    public Date arrivalAt;
    public Time arrivalWhen;
    public String status;
    public Integer driver;
}
