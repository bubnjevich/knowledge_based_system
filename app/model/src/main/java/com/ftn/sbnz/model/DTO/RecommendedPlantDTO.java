package com.ftn.sbnz.model.DTO;

public class RecommendedPlantDTO {
    private Long id;
    private String name;
    private PlantLifespan plantLifespan;

    public RecommendedPlantDTO() {
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
}
