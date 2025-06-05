package com.mykola.railroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AggregateTrainInfoDTO {
    public TrainDTO train;
    public TrainServiceDTO service;
    public Integer routeLength;
    public Float ticketCost;
}
