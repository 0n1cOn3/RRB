package com.mykola.railroad.controller;

import com.mykola.railroad.dto.TrainDTO;
import com.mykola.railroad.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {
    @Autowired
    private StationService stationService;

    @GetMapping("/{id}/train")
    public List<TrainDTO> getStationTrains(@PathVariable Integer id) {
        return stationService.getStationTrains(id);
    }
}
