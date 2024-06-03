package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.DTO.WeatherConditionDTO;
import com.ftn.sbnz.model.WeatherCondition;
import com.ftn.sbnz.service.services.interfaces.IWeatherConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/weather-conditions")
public class WeatherConditionController {

    private IWeatherConditionService weatherConditionService;

    @Autowired
    public WeatherConditionController(IWeatherConditionService weatherConditionService) {
        this.weatherConditionService = weatherConditionService;
    }

    @PostMapping
    public ResponseEntity createWeatherCondition(@RequestBody WeatherConditionDTO weatherConditionDTO) {
        this.weatherConditionService.save(weatherConditionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
