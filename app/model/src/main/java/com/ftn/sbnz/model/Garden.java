package com.ftn.sbnz.model;

public class Garden {

    private final Long id;
    private double sizeInSquareMeters;
    private SoilType soilType;
    private int directSunlightExposureInHours;
    private Climate climate;

    public Garden(Long id, double sizeInSquareMeters, SoilType soilType, int directSunlightExposureInHours, Climate climate) {
        this.id = id;
        this.sizeInSquareMeters = sizeInSquareMeters;
        this.soilType = soilType;
        this.directSunlightExposureInHours = directSunlightExposureInHours;
        this.climate = climate;
    }

    public Long getId() {
        return id;
    }

    public double getSizeInSquareMeters() {
        return sizeInSquareMeters;
    }

    public void setSizeInSquareMeters(double sizeInSquareMeters) {
        this.sizeInSquareMeters = sizeInSquareMeters;
    }

    public SoilType getSoilType() {
        return soilType;
    }

    public void setSoilType(SoilType soilType) {
        this.soilType = soilType;
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
