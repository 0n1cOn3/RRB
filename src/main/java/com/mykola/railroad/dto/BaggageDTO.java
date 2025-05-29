package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaggageDTO {
    public Integer ticket;
    public Float weight;
    public String status;
}
