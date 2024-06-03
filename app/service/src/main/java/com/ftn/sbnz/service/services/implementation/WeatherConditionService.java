package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.DTO.WeatherConditionDTO;
import com.ftn.sbnz.model.WeatherCondition;
import com.ftn.sbnz.service.repositories.WeatherConditionRepository;
import com.ftn.sbnz.service.services.interfaces.IWeatherConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherConditionService implements IWeatherConditionService {

    private final WeatherConditionRepository weatherConditionRepository;

    @Autowired
    public WeatherConditionService(WeatherConditionRepository weatherConditionRepository) {
        this.weatherConditionRepository = weatherConditionRepository;
    }

    public void save(WeatherConditionDTO weatherConditionDTO) {
        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setmeasurementDate(weatherConditionDTO.getMeasurementDate());
        weatherCondition.setPrecipitation(weatherConditionDTO.getPrecipitation());
        weatherCondition.setTemperature(weatherConditionDTO.getTemperature());
        this.weatherConditionRepository.save(weatherCondition);
    }


    public List<WeatherCondition> getAllWeatherConditionsSortedByDate() {
        return weatherConditionRepository.findAll(Sort.by(Sort.Direction.ASC, "measurementDate"));
    }

}
