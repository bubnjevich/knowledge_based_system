package com.ftn.sbnz.service.services.interfaces;

import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantsForAlarms;
import com.ftn.sbnz.model.SimilarPlant;

import java.util.Set;

public interface IPlantAdviceService {
    public Set<RecommendedPlantDTO> getRecommendedPlant(AdviceRequestDTO adviceRequestDTO);
    public Set<RecommendedPlantDTO> getSimilarPlantWithSpecificLevel(SimilarPlant similarPlant);

    public RecommendedPlantsForAlarms getPlantForWeatherCondition();
}
