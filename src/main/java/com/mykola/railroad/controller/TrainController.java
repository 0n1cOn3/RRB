package com.mykola.railroad.controller;

import com.mykola.railroad.dto.InspectionSearchDTO;
import com.mykola.railroad.dto.TrainDTO;
import com.mykola.railroad.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
