package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "FloweringPlants")
public class FloweringPlant extends PerennialPlant implements Serializable {
    @Enumerated(EnumType.STRING)
    private FlowerColor flowerColor;

    @ElementCollection(targetClass = Season.class)
    @Enumerated(EnumType.STRING)
    private List<Season> floweringSeason;

    public FloweringPlant(String name, PlantType plantType, List<Climate> suitableClimates, List<Soil> suitableSoilTypes,
                          List<PlantFunctionality> plantFunctionalities, double minTemperature, double maxTemperature, Humidity humidity, double height, int lightHoursNeeded) {
        super(name, plantType, suitableClimates, suitableSoilTypes, plantFunctionalities, minTemperature, maxTemperature, humidity, height, lightHoursNeeded);
    }

    public FloweringPlant() {

    }

    public FloweringPlant(String name, PlantType plantType, List<Climate> suitableClimates, List<Soil> suitableSoilTypes, List<PlantFunctionality> plantFunctionalities, double minTemperature, double maxTemperature,
                          Humidity humidity, double height, FlowerColor flowerColor, List<Season> floweringSeason, int lightHoursNeeded) {
        super(name, plantType, suitableClimates, suitableSoilTypes, plantFunctionalities, minTemperature, maxTemperature, humidity, height, lightHoursNeeded);
        this.flowerColor = flowerColor;
        this.floweringSeason = floweringSeason;
    }

    public FlowerColor getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(FlowerColor flowerColor) {
        this.flowerColor = flowerColor;
    }

    public List<Season> getFloweringSeason() {
        return floweringSeason;
    }

    public void setFloweringSeason(List<Season> floweringSeason) {
        this.floweringSeason = floweringSeason;
    }
}
