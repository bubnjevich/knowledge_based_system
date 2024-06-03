package com.ftn.sbnz.model.DTO;

import com.ftn.sbnz.model.WeatherCondition;

import javax.persistence.Column;
import java.time.LocalDate;

public class WeatherConditionDTO {

    private LocalDate measurementDate;
    private double temperature;
    private double precipitation;

    public WeatherConditionDTO() {

    }

    public WeatherConditionDTO(LocalDate measurementDate, double temperature, double precipitation) {
        this.measurementDate = measurementDate;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public LocalDate getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDate measurementDate) {
        this.measurementDate = measurementDate;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }
}
