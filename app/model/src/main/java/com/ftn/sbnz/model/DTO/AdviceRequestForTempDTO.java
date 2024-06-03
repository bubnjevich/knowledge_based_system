package com.ftn.sbnz.model.DTO;

import com.ftn.sbnz.model.enums.Climate;
import com.ftn.sbnz.model.enums.FlowerColor;
import com.ftn.sbnz.model.enums.PlantFunctionality;
import com.ftn.sbnz.model.enums.PlantType;

import java.util.ArrayList;
import java.util.List;

public class AdviceRequestForTempDTO {

    private PlantType plantType;
    private int lightHoursNeeded;
    private List<PlantFunctionality> plantFunctionality;
    private FlowerColor flowerColor;

    private double minTemperature;
    private double maxTemperature;

    private List<String> climates;

    public AdviceRequestForTempDTO() {
        this.climates = new ArrayList<>();
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public List<String> getClimates() {
        return climates;
    }

    public void setClimates(List<String> climates) {
        this.climates = climates;
    }

    public int getLightHoursNeeded() {
        return lightHoursNeeded;
    }

    public void setLightHoursNeeded(int lightHoursNeeded) {
        this.lightHoursNeeded = lightHoursNeeded;
    }

    public List<PlantFunctionality> getPlantFunctionality() {
        return plantFunctionality;
    }

    public void setPlantFunctionality(List<PlantFunctionality> plantFunctionality) {
        this.plantFunctionality = plantFunctionality;
    }

    public FlowerColor getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(FlowerColor flowerColor) {
        this.flowerColor = flowerColor;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
