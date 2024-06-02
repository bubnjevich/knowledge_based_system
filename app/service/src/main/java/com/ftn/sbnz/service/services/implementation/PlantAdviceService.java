package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.service.repositories.AnnualPlantRepository;
import com.ftn.sbnz.service.repositories.FloweringPlantRepository;
import com.ftn.sbnz.service.repositories.PerennialPlantRepository;
import com.ftn.sbnz.service.repositories.PlantRepository;
import com.ftn.sbnz.service.services.interfaces.IPlantAdviceService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaGroup;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlantAdviceService implements IPlantAdviceService {

    private final KieContainer kieContainer;
    private PlantRepository plantRepository;
    private FloweringPlantRepository floweringPlantRepository;
    private PerennialPlantRepository perennialPlantRepository;
    private AnnualPlantRepository annualPlantRepository;

    @Autowired
    public PlantAdviceService(KieContainer kieContainer, PlantRepository plantRepository, FloweringPlantRepository floweringPlantRepository,
                              PerennialPlantRepository perennialPlantRepository, AnnualPlantRepository annualPlantRepository) {
        this.kieContainer = kieContainer;
        this.plantRepository = plantRepository;
        this.floweringPlantRepository = floweringPlantRepository;
        this.perennialPlantRepository = perennialPlantRepository;
        this.annualPlantRepository = annualPlantRepository;
    }

    @Override
    public Set<RecommendedPlantDTO> getSimilarPlantWithSpecificLevel(SimilarPlant similarPlant) {
        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        KieSession kieSession = kieContainer.newKieSession("backwardSession");
        kieSession.setGlobal("recommendations", recommendations);
        AgendaGroup agendaGroup = kieSession.getAgenda().getAgendaGroup("level" + similarPlant.getLevel());
        agendaGroup.setFocus();
        kieSession.insert(similarPlant);
        List<Plant> plants = plantRepository.findAll();
        List<FloweringPlant> floweringPlants = floweringPlantRepository.findAll();
        List<PerennialPlant> perennialPlants = perennialPlantRepository.findAll();
        kieSession.insert(new ParentChildBackward("FloweringPlant", "PerennialPlant"));
        kieSession.insert(new ParentChildBackward("PerennialPlant", "Plant"));
        kieSession.insert(new ParentChildBackward("NotFloweringAnnualPlant", "Plant"));
        kieSession.insert(new ParentChildBackward("FloweringAnnualPlant", "Plant"));

        kieSession.insert(plants);
        for (Plant p : plants) {
            kieSession.insert(p);
            kieSession.insert(new ParentChildBackward(p.getName(), "Plant"));
        }

        for (FloweringPlant p : floweringPlants) {
            kieSession.insert(p);
            kieSession.insert(new ParentChildBackward(p.getName(), "FloweringPlant"));
        }

        for (PerennialPlant p : perennialPlants) {
            kieSession.insert(p);
            kieSession.insert(new ParentChildBackward(p.getName(), "PerennialPlant"));

        }


        kieSession.fireAllRules();
        kieSession.dispose();
        return recommendations;
    }

    public Set<RecommendedPlantDTO> getRecommendedPlant(AdviceRequestDTO adviceRequests) {
        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        KieSession kieSession = kieContainer.newKieSession("kSession");
        kieSession.setGlobal("recommendations", recommendations);

        kieSession.insert(adviceRequests);

        List<Plant> plants = plantRepository.findAll();
        List<FloweringPlant> floweringPlants = floweringPlantRepository.findAll();
        List<AnnualPlant> annualPlants = annualPlantRepository.findAll();
        List<PerennialPlant> perennialPlants = perennialPlantRepository.findAll();

        System.out.println(adviceRequests.getPlantFunctionality());
        kieSession.insert(plants);
        for (Plant p : plants) {
            kieSession.insert(p);

        }

        for (FloweringPlant p : floweringPlants) {
            kieSession.insert(p);
        }



        kieSession.fireAllRules();
        kieSession.dispose();
        return recommendations;
    }

}
