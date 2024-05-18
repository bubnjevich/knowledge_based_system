package com.ftn.sbnz.model;

import javax.persistence.*;

@Entity
public class Humidity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double humidityRangeMin;
    @Column
    private double humidityRangeMax;

    public Humidity() {
    }

    public Humidity(Long id, double humidityRangeMin, double humidityRangeMax) {
        this.id = id;
        this.humidityRangeMin = humidityRangeMin;
        this.humidityRangeMax = humidityRangeMax;
    }

    public double getHumidityRangeMin() {
        return humidityRangeMin;
    }

    public void setHumidityRangeMin(double humidityRangeMin) {
        this.humidityRangeMin = humidityRangeMin;
    }

    public double getHumidityRangeMax() {
        return humidityRangeMax;
    }

    public void setHumidityRangeMax(double humidityRangeMax) {
        this.humidityRangeMax = humidityRangeMax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
