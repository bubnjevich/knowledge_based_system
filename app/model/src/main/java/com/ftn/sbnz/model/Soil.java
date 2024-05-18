package com.ftn.sbnz.model;

import javax.persistence.*;

@Entity
public class Soil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String soilType;
    @Column
    private double minPhValue;
    @Column
    private double maxPhValue;

    public Soil() {
    }

    public Soil(String soilType, double minPhValue, double maxPhValue) {
        this.soilType = soilType;
        this.minPhValue = minPhValue;
        this.maxPhValue = maxPhValue;
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

    public double getMinPhValue() {
        return minPhValue;
    }

    public void setMinPhValue(double minPhValue) {
        this.minPhValue = minPhValue;
    }

    public double getMaxPhValue() {
        return maxPhValue;
    }

    public void setMaxPhValue(double maxPhValue) {
        this.maxPhValue = maxPhValue;
    }
}
