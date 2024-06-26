package com.ftn.sbnz.model.DTO;

import com.ftn.sbnz.model.Soil;
import com.ftn.sbnz.model.enums.FlowerColor;
import com.ftn.sbnz.model.enums.PlantFunctionality;
import com.ftn.sbnz.model.enums.PlantType;

import java.util.List;

public class AdviceRequestDTO {

    private PlantType plantType;
    private List<String> soilType;
    private int lightHoursNeeded;
    private List<PlantFunctionality> plantFunctionality;
    private FlowerColor flowerColor;

    private double soilPh;

    public AdviceRequestDTO() {
    }



    public double getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(double soilPh) {
        this.soilPh = soilPh;
    }

    public FlowerColor getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(FlowerColor flowerColor) {
        this.flowerColor = flowerColor;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }



    public int getLightHoursNeeded() {
        return lightHoursNeeded;
    }

    public void setLightHoursNeeded(int lightHoursNeeded) {
        this.lightHoursNeeded = lightHoursNeeded;
    }


    public List<String> getSoilType() {
        return soilType;
    }

    public void setSoilType(List<String> soilType) {
        this.soilType = soilType;
    }

    public List<PlantFunctionality> getPlantFunctionality() {
        return plantFunctionality;
    }

    public void setPlantFunctionality(List<PlantFunctionality> plantFunctionality) {
        this.plantFunctionality = plantFunctionality;
    }

    @Override
    public String toString() {
        return "AdviceRequestDTO{" +
                "plantType=" + plantType +
                ", soilType='" + soilType + '\'' +
                ", lightHoursNeeded=" + lightHoursNeeded +
                ", plantFunctionality=" + plantFunctionality +
                ", flowerColor=" + flowerColor +
                '}';
    }
}
