package com.mykola.railroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainAndServiceCountDTO {
    public TrainDTO train;
    public TrainServiceDTO service;
    public Integer servicesTotal;
}
