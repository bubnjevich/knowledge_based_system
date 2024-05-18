package com.ftn.sbnz.service.services.interfaces;

import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;

import java.util.Set;

public interface IPlantAdviceService {
    public Set<RecommendedPlantDTO> getRecommendedPlant(AdviceRequestDTO adviceRequestDTO);
}
