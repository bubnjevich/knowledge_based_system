package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.service.services.interfaces.IPlantAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "api/advices")
public class PlantAdviceController {
    private IPlantAdviceService plantAdviceService;

    @Autowired
    public PlantAdviceController(IPlantAdviceService plantAdviceService) {
        this.plantAdviceService = plantAdviceService;
    }

    @PutMapping()
    public ResponseEntity<Set<RecommendedPlantDTO>> recommend(@RequestBody AdviceRequestDTO adviceRequestDTO) {
        System.out.println(adviceRequestDTO);
        return new ResponseEntity<>(this.plantAdviceService.getRecommendedPlant(adviceRequestDTO), HttpStatus.OK);
    }
}
