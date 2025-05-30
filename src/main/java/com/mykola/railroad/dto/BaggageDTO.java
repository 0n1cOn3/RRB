package com.mykola.railroad.dto;

import lombok.Data;

@Data
public class BaggageDTO {
    public Integer ticket;
    public Float weight;
    public TypeBaggageStatus status;
}
