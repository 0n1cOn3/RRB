package com.mykola.railroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelayServiceDTO {
    public DelayDTO delay;
    public TrainServiceDTO service;
}
