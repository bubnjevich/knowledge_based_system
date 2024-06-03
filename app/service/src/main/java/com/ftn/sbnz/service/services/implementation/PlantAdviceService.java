package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantsForAlarms;
import com.ftn.sbnz.model.enums.AlarmType;
import com.ftn.sbnz.service.repositories.*;
import com.ftn.sbnz.service.services.interfaces.IPlantAdviceService;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaGroup;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class PlantAdviceService implements IPlantAdviceService {

    private final KieContainer kieContainer;
    private PlantRepository plantRepository;
    private FloweringPlantRepository floweringPlantRepository;
    private PerennialPlantRepository perennialPlantRepository;
    private AnnualPlantRepository annualPlantRepository;

    private SoilMoistureRepository soilMoistureRepository;

    private WeatherConditionService weatherConditionService;

    @Autowired
    public PlantAdviceService(KieContainer kieContainer, PlantRepository plantRepository, FloweringPlantRepository floweringPlantRepository,
                              PerennialPlantRepository perennialPlantRepository, AnnualPlantRepository annualPlantRepository,
                              SoilMoistureRepository soilMoistureRepository,
                              WeatherConditionService weatherConditionService) {
        this.kieContainer = kieContainer;
        this.plantRepository = plantRepository;
        this.floweringPlantRepository = floweringPlantRepository;
        this.perennialPlantRepository = perennialPlantRepository;
        this.annualPlantRepository = annualPlantRepository;
        this.soilMoistureRepository = soilMoistureRepository;
        this.weatherConditionService = weatherConditionService;
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


    @Override
    public RecommendedPlantsForAlarms getPlantForWeatherCondition() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("cepConfigKsessionPseudoClock");
        RecommendedPlantsForAlarms recommendedPlantsForAlarms = new RecommendedPlantsForAlarms();

        List<Plant> plants = plantRepository.findAll();
        List<FloweringPlant> floweringPlants = floweringPlantRepository.findAll();
        List<PerennialPlant> perennialPlants = perennialPlantRepository.findAll();

        kieSession.insert(plants);
        for (Plant p : plants) {
            kieSession.insert(p);
        }
        for (FloweringPlant p : floweringPlants) {
            kieSession.insert(p);
        }

        for (PerennialPlant p : perennialPlants) {
            kieSession.insert(p);
        }

        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        kieSession.setGlobal("recommendations", recommendations);
        kieSession.setGlobal("recommendationsForAlarms", recommendedPlantsForAlarms);

        SessionPseudoClock clock = kieSession.getSessionClock();

        List<WeatherCondition> weatherConditions = weatherConditionService.getAllWeatherConditionsSortedByDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDate = currentDate;

        for (WeatherCondition weatherCondition : weatherConditions) {
            kieSession.insert(weatherCondition);
            System.out.println(previousDate);
            System.out.println(weatherCondition.getmeasurementDate());

            long daysDifference = ChronoUnit.DAYS.between(weatherCondition.getmeasurementDate(), currentDate);
            System.out.println("daysDifference: " + daysDifference);

            clock.advanceTime(daysDifference, TimeUnit.DAYS);

            previousDate = weatherCondition.getmeasurementDate();
        }

        List<SoilMoisture> soilMoistureList = soilMoistureRepository.findAllByOrderByMeasurementDateAsc();
         previousDate = currentDate;

        for (SoilMoisture soilMoisture : soilMoistureList) {
            kieSession.insert(soilMoisture);

            long daysDifference = ChronoUnit.DAYS.between(previousDate, currentDate);

            clock.advanceTime(daysDifference, TimeUnit.DAYS);

            previousDate = soilMoisture.getmeasurementDate();
        }


        kieSession.fireAllRules();
        if (!recommendedPlantsForAlarms.getRecommendedPlants().isEmpty()) {
            recommendedPlantsForAlarms.setAlarmMessage("Drought detected based on the last 5 days of weather data.");
            recommendedPlantsForAlarms.setAlarmType(AlarmType.DROUGHT);
        }

        return recommendedPlantsForAlarms;

    }
}
