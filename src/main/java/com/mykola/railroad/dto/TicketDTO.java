package com.mykola.railroad.dto;

import lombok.Data;

@Data
public class TicketDTO {
    public Integer id;
    public Integer customer;
    public Integer trainService;
    public String seat;
    public Float cost;
    public TypePassenger passenger;
    public String status;
}
