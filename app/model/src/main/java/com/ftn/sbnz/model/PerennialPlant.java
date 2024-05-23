package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.Climate;
import com.ftn.sbnz.model.enums.PlantFunctionality;
import com.ftn.sbnz.model.enums.PlantType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PerennialPlants")
public class PerennialPlant extends Plant implements Serializable {
    @Column
    private boolean frostResistance;
    @Column
    private boolean isConifer;
    public PerennialPlant() {
        super();
    }

    public PerennialPlant(String name, PlantType plantType, List<Climate> suitableClimates, List<Soil> suitableSoilTypes, List<PlantFunctionality> plantFunctionalities, double minTemperature,
                          double maxTemperature, Humidity humidity, double height, int lightHoursNeeded) {
        super(name, plantType, suitableClimates, suitableSoilTypes, plantFunctionalities, minTemperature, maxTemperature, humidity, height, lightHoursNeeded);
    }

    public boolean isFrostResistance() {
        return frostResistance;
    }

    public void setFrostResistance(boolean frostResistance) {
        this.frostResistance = frostResistance;
    }

    public boolean isConifer() {
        return isConifer;
    }

    public void setConifer(boolean conifer) {
        isConifer = conifer;
    }
}
