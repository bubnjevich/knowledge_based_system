package com.ftn.sbnz.model;

import javax.persistence.*;

@Entity
public class Soil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String soilType;


    public Soil() {
    }

    public Soil(String soilType) {
        this.soilType = soilType;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

}
