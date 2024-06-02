package com.ftn.sbnz.model;

public class SimilarPlant {

    private String name;
    private int level;

    public SimilarPlant(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimilarPlant{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
