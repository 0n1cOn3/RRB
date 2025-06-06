package com.mykola.railroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregateTicketInfoDTO {
    public Integer route;
    public Integer ticketCount;
    public Float ticketCost;
    public Integer routeLength;
}
