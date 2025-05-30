package com.mykola.railroad.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class TrainServiceDTO {
    public Integer id;
    public Integer train;
    public Integer route;
    public Date departureAt;
    public Time departureWhen;
    public Date arrivalAt;
    public Time arrivalWhen;
    public TypeTrainStatus status;
    public Integer driver;
}
