package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.Climate;

import javax.persistence.*;

@Entity
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private double sizeInSquareMeters;

    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "soilId")
    private Soil soil;
    private int directSunlightExposureInHours;
    @Enumerated(EnumType.STRING)
    private Climate climate;

    public Garden() {
    }

    public Garden(double sizeInSquareMeters, Soil soil, int directSunlightExposureInHours, Climate climate) {
        this.sizeInSquareMeters = sizeInSquareMeters;
        this.soil = soil;
        this.directSunlightExposureInHours = directSunlightExposureInHours;
        this.climate = climate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSizeInSquareMeters() {
        return sizeInSquareMeters;
    }

    public void setSizeInSquareMeters(double sizeInSquareMeters) {
        this.sizeInSquareMeters = sizeInSquareMeters;
    }

    public Soil getSoil() {
        return soil;
    }

    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    public int getDirectSunlightExposureInHours() {
        return directSunlightExposureInHours;
    }

    public void setDirectSunlightExposureInHours(int directSunlightExposureInHours) {
        this.directSunlightExposureInHours = directSunlightExposureInHours;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }
}
