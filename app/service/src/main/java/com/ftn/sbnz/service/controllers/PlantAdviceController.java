package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.AdviceRequestForTempDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantsForAlarms;
import com.ftn.sbnz.model.SimilarPlant;
import com.ftn.sbnz.service.services.interfaces.IPlantAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "api/advices")
public class PlantAdviceController {
    private IPlantAdviceService plantAdviceService;

    @Autowired
    public PlantAdviceController(IPlantAdviceService plantAdviceService) {
        this.plantAdviceService = plantAdviceService;
    }

    @PutMapping("/fullInfo")
    public ResponseEntity<Set<RecommendedPlantDTO>> recommend(@RequestBody AdviceRequestDTO adviceRequestDTO) {
        System.out.println(adviceRequestDTO);
        return new ResponseEntity<>(this.plantAdviceService.getRecommendedPlant(adviceRequestDTO), HttpStatus.OK);
    }

    @PutMapping("/similarPlants")
    public ResponseEntity<Set<RecommendedPlantDTO>> recommendSimilarPlant(@RequestBody SimilarPlant similarPlant) {
        System.out.println(similarPlant);
        return new ResponseEntity<>(this.plantAdviceService.getSimilarPlantWithSpecificLevel(similarPlant), HttpStatus.OK);
    }

    @PutMapping("/fullInfoClimate")
    public ResponseEntity<Set<RecommendedPlantDTO>> recommendPlantsForSpecificClimate(@RequestBody AdviceRequestForTempDTO dto) {
        System.out.println(dto);
        return new ResponseEntity<>(this.plantAdviceService.recommendPlantsForSpecificClimates(dto), HttpStatus.OK);
    }

    @GetMapping("/drought-resistance-alarms")
    public ResponseEntity<RecommendedPlantsForAlarms> recommendPlantForWeatherCondition() {
        return new ResponseEntity<>(this.plantAdviceService.getPlantForWeatherCondition(),HttpStatus.OK);
    }

    @GetMapping("/optimal-sowing-plants")
    public ResponseEntity<RecommendedPlantsForAlarms> recommendPlantsForSowing() {
        return new ResponseEntity<>(this.plantAdviceService.getPlantsForSowing(), HttpStatus.OK);
    }
}
