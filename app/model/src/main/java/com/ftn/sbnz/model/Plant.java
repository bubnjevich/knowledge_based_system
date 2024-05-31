package com.ftn.sbnz.model;

import com.ftn.sbnz.model.enums.*;

import javax.persistence.*;

import java.util.List;


    @Entity
    @Inheritance(strategy = InheritanceType.JOINED)
    public class Plant {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column
        private String name;
        @Enumerated(EnumType.STRING)
        @Column(name = "plantType")
        private PlantType plantType;
        @ElementCollection(targetClass = Climate.class)
        @Enumerated(EnumType.STRING)
        private List<Climate> suitableClimates;
        @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
        private List<Soil> suitableSoilTypes;
        @ElementCollection(targetClass = PlantFunctionality.class, fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        private List<PlantFunctionality> plantFunctionalities;

        @ManyToOne
        @JoinColumn(name = "humidityId")
        private Humidity humidity;
        @Column(name = "light_hours_needed")
        private int lightHoursNeeded;

        private double height;


    public Plant() {
    }

    public Plant(String name, PlantType plantType, List<Climate> suitableClimates, List<Soil> suitableSoilTypes,
                 List<PlantFunctionality> plantFunctionalities,
                 Humidity humidity, double height, int lightHoursNeeded) {
        this.name = name;
        this.plantType = plantType;
        this.suitableClimates = suitableClimates;
        this.suitableSoilTypes = suitableSoilTypes;
        this.plantFunctionalities = plantFunctionalities;

        this.humidity = humidity;
        this.height = height;
        this.lightHoursNeeded = lightHoursNeeded;
    }


        public int getLightHoursNeeded() {
            return lightHoursNeeded;
        }

        public void setLightHoursNeeded(int lightHoursNeeded) {
            this.lightHoursNeeded = lightHoursNeeded;
        }

        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public List<Climate> getSuitableClimates() {
        return suitableClimates;
    }

    public void setSuitableClimates(List<Climate> suitableClimates) {
        this.suitableClimates = suitableClimates;
    }

    public List<Soil> getSuitableSoilTypes() {
        return suitableSoilTypes;
    }

    public void setSuitableSoilTypes(List<Soil> suitableSoilTypes) {
        this.suitableSoilTypes = suitableSoilTypes;
    }

    public List<PlantFunctionality> getPlantFunctionalities() {
        return plantFunctionalities;
    }

    public void setPlantFunctionalities(List<PlantFunctionality> plantFunctionalities) {
        this.plantFunctionalities = plantFunctionalities;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }


}
