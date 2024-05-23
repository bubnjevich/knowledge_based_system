package com.ftn.sbnz.model;

import org.kie.api.definition.type.Role;

import javax.persistence.GenerationType;
import java.time.LocalDate;
import javax.persistence.*;


@Entity
@Role(Role.Type.EVENT)

public class SoilMoisture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate measurementDate;

    @Column(name = "moisture_level", nullable = false)
    private double moistureLevel;

    public SoilMoisture() {

    }

    public Long getId() {
        return id;
    }

    public SoilMoisture(String measurementDate, double moistureLevel) {
        this.moistureLevel = moistureLevel;
        this.measurementDate = LocalDate.parse(measurementDate);

    }

    public SoilMoisture(LocalDate measurementDate, double moistureLevel) {
        this.measurementDate = measurementDate;
        this.moistureLevel = moistureLevel;
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

    public double getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(double moistureLevel) {
        this.moistureLevel = moistureLevel;
    }
}
