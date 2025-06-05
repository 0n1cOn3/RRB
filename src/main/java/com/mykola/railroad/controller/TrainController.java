package com.mykola.railroad.controller;

import com.mykola.railroad.dto.AggregateTrainInfoDTO;
import com.mykola.railroad.dto.InspectionSearchDTO;
import com.mykola.railroad.dto.TrainDTO;
import com.mykola.railroad.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {
    @Autowired
    private TrainService trainService;

    @PostMapping("/inspection/search")
    public List<TrainDTO> searchInspectedTrains(@RequestBody @Valid InspectionSearchDTO search) {
        return trainService.searchInspectedTrains(search);
    }

    @GetMapping("/aggregate")
    public List<AggregateTrainInfoDTO> aggregateTrainInfo() {
        return trainService.aggregateTrainInfo();
    }
}
