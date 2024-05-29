package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "AnnualPlants")
public class AnnualPlant extends Plant implements Serializable {
    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;
    @Enumerated(EnumType.STRING)
    @Column(name = "flowerColor")
    private FlowerColor flowerColor;

    public AnnualPlant() {
        super();
    }

    public AnnualPlant(String name, PlantType plantType, List<Climate> suitableClimates, List<Soil> suitableSoilTypes, List<PlantFunctionality> plantFunctionalities, double minTemperature,
                       double maxTemperature, Humidity humidity, double height, Season season, FlowerColor flowerColor, int lightHoursNeeded) {
        super(name, plantType, suitableClimates, suitableSoilTypes, plantFunctionalities, humidity, height, lightHoursNeeded);
        this.season = season;
        this.flowerColor = flowerColor;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public FlowerColor getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(FlowerColor flowerColor) {
        this.flowerColor = flowerColor;
    }
}

