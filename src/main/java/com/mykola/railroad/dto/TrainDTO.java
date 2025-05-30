package com.mykola.railroad.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TrainDTO {
    public Integer id;
    public TypeTrain trainType;
    public Boolean inService;
    public Integer driverBrigade;
    public Integer serviceBrigade;
    public Date installed;
}
