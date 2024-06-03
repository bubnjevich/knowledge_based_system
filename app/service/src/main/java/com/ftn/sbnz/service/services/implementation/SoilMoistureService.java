package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.SoilMoisture;
import com.ftn.sbnz.service.repositories.SoilMoistureRepository;
import com.ftn.sbnz.service.services.interfaces.ISoilMoistureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilMoistureService implements ISoilMoistureService {

    private final SoilMoistureRepository soilMoistureRepository;

    @Autowired
    public SoilMoistureService(SoilMoistureRepository soilMoistureRepository) {
        this.soilMoistureRepository = soilMoistureRepository;
    }

    @Override
    public void save(SoilMoisture soilMoisture) {
        this.soilMoistureRepository.save(soilMoisture);
    }

    public List<SoilMoisture> getAllSoilMoistureSortedByDate() {
        return soilMoistureRepository.findAll(Sort.by(Sort.Direction.ASC, "measurementDate"));
    }

}
