package com.ftn.sbnz.model.DTO;

import com.ftn.sbnz.model.enums.AlarmType;

import java.util.HashSet;
import java.util.Set;

public class RecommendedPlantsForAlarms {

    private AlarmType alarmType;
    private String alarmMessage;
    private Set<RecommendedPlantDTO> recommendedPlants;

    public RecommendedPlantsForAlarms() {
        this.recommendedPlants = new HashSet<>();
    }

    public RecommendedPlantsForAlarms(AlarmType alarmType, String alarmMessage, Set<RecommendedPlantDTO> recommendedPlants) {
        this.alarmType = alarmType;
        this.alarmMessage = alarmMessage;
        this.recommendedPlants = recommendedPlants;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public Set<RecommendedPlantDTO> getRecommendedPlants() {
        return recommendedPlants;
    }

    public void setRecommendedPlants(Set<RecommendedPlantDTO> recommendedPlants) {
        this.recommendedPlants = recommendedPlants;
    }
}
