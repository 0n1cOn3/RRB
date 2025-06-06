package com.mykola.railroad.controller;

import com.mykola.railroad.dto.AggregateTicketInfoDTO;
import com.mykola.railroad.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/aggregate")
    public List<AggregateTicketInfoDTO> aggregateTicketInfo(@RequestParam String from, @RequestParam String to) {
        return ticketService.aggregateTicketInfo(from, to);
    }
}
