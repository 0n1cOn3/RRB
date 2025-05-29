package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrainDTO {
    public Integer id;
    public String trainType;
    public Boolean inService;
    public Integer driverBrigade;
    public Integer serviceBrigade;
    public Date installed;
}
