package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    public Integer id;
    public Integer customer;
    public Integer trainService;
    public String seat;
    public String passenger;
    public String status;
}
