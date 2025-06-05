package com.mykola.railroad.controller;

import com.mykola.railroad.dto.TrainServiceDTO;
import com.mykola.railroad.service.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delay")
public class DelayController {
    @Autowired
    private DelayService delayService;

    @GetMapping("/route/{id}")
    public List<TrainServiceDTO> getCanceledServicesOnRoute(@PathVariable Integer id) {
        return delayService.getCanceledServicesOnRoute(id);
    }

    @GetMapping("/station/{id}")
    public List<TrainServiceDTO> getCanceledServicesOnStation(@PathVariable Integer id) {
        return delayService.getCanceledServicesToStation(id);
    }
}
