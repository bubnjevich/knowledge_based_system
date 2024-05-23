package com.ftn.sbnz.model;

import org.kie.api.definition.type.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Role(Role.Type.EVENT)
public class WeatherCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate measurementDate;
    @Column
    private double temperature;
    @Column
    private double precipitation;

    public WeatherCondition(String measurementDate, double temperature, double precipitation) {
        this.measurementDate = LocalDate.parse(measurementDate);
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public WeatherCondition(LocalDate measurementDate, double temperature, double precipitation) {
        this.measurementDate = measurementDate;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public WeatherCondition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getmeasurementDate() {
        return measurementDate;
    }

    public void setmeasurementDate(LocalDate measurementDate) {
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
