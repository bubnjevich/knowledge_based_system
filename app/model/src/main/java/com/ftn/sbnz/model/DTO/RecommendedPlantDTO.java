package com.ftn.sbnz.model.DTO;

import com.ftn.sbnz.model.Soil;
import com.ftn.sbnz.model.enums.PlantFunctionality;
import com.ftn.sbnz.model.enums.PlantType;

import java.util.List;

public class RecommendedPlantDTO {
    private Long id;
    private String name;
    private PlantLifespan plantLifespan;

    private PlantType plantType;

    private List<PlantFunctionality> functionalities;

    private List<Soil> suitableSoils;

    private double height;

    private int lightHoursNeeded;

    public RecommendedPlantDTO() {
    }

    public int getLightHoursNeeded() {
        return lightHoursNeeded;
    }

    public void setLightHoursNeeded(int lightHoursNeeded) {
        this.lightHoursNeeded = lightHoursNeeded;
    }

    public double getHeight() {
        return height;
    }

    public List<PlantFunctionality> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(List<PlantFunctionality> functionalities) {
        this.functionalities = functionalities;
    }

    public List<Soil> getSuitableSoils() {
        return suitableSoils;
    }

    public void setSuitableSoils(List<Soil> suitableSoils) {
        this.suitableSoils = suitableSoils;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantLifespan getPlantLifespan() {
        return plantLifespan;
    }

    public void setPlantLifespan(PlantLifespan plantLifespan) {
        this.plantLifespan = plantLifespan;
    }

    @Override
    public String toString() {
        return "RecommendedPlantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plantLifespan=" + plantLifespan +
                '}';
    }
}
