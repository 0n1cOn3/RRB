package com.mykola.railroad.dto;

import lombok.Data;

@Data
public class DelayWithTicketsDTO {
    public DelayServiceDTO delay;
    public TicketDTO ticket;
}
