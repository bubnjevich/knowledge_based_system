package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.AdviceRequestForTempDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantsForAlarms;
import com.ftn.sbnz.model.enums.AlarmType;
import com.ftn.sbnz.service.repositories.*;
import com.ftn.sbnz.service.services.interfaces.IPlantAdviceService;
import org.drools.core.time.SessionPseudoClock;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaGroup;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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



    @Override
    public Set<RecommendedPlantDTO> getRecommendedPlant(AdviceRequestDTO adviceRequests) {

        if (adviceRequests.getSoilPh() != 0) {
            InputStream template = PlantAdviceService.class.getResourceAsStream("/rules/template/soil-classification.drt");
            InputStream data = PlantAdviceService.class.getResourceAsStream("/rules/template/soil-classification.xls");

            ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
            String drl = converter.compile(data, template, 3, 2);
            KieSession ksession = this.createKieSessionFromDRL(drl);
            ksession.insert(adviceRequests);
            ksession.fireAllRules();
            if (adviceRequests.getSoilType().isEmpty()) {
                return new HashSet<>();
            }

        }
        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        KieSession kieSession = kieContainer.newKieSession("kSession");
        kieSession.setGlobal("recommendations", recommendations);

        kieSession.insert(adviceRequests);

        List<Plant> plants = plantRepository.findAll();
        List<FloweringPlant> floweringPlants = floweringPlantRepository.findAll();

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

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }

    @Override
    public RecommendedPlantsForAlarms getPlantsForSowing() {
        System.out.println("started sowing!");
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("cepConfigKsessionPseudoClock2");
        RecommendedPlantsForAlarms recommendedPlantsForAlarms = new RecommendedPlantsForAlarms();

        List<AnnualPlant> annualPlants = annualPlantRepository.findAll();

        for (AnnualPlant p : annualPlants) {
            kieSession.insert(p);
        }

        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        kieSession.setGlobal("recommendations", recommendations);
        kieSession.setGlobal("recommendationsForAlarms", recommendedPlantsForAlarms);

        SessionPseudoClock clock = kieSession.getSessionClock();

        List<WeatherCondition> weatherConditions = weatherConditionService.getLastSixWeatherConditionsSortedByDate();

        for (WeatherCondition weatherCondition : weatherConditions) {
            System.out.println(weatherCondition.getmeasurementDate());
            kieSession.insert(weatherCondition);
            clock.advanceTime(1, TimeUnit.DAYS);
        }

        List<SoilMoisture> soilMoistureList = soilMoistureRepository.findAllByOrderByMeasurementDateAsc();

        for (SoilMoisture soilMoisture : soilMoistureList) {
            kieSession.insert(soilMoisture);
        }
        kieSession.fireAllRules();
        if (!recommendedPlantsForAlarms.getRecommendedPlants().isEmpty()) {
            recommendedPlantsForAlarms.setAlarmMessage("Optimal weather conditions detected for sowing based on the last 10 days of weather data.");
            recommendedPlantsForAlarms.setAlarmType(AlarmType.OPTIMAL_PLANTING_CONDITIONS);
        } else {
            recommendedPlantsForAlarms.setAlarmMessage("There is no optimal weather for sowing");

        }

        return recommendedPlantsForAlarms;
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

        List<WeatherCondition> weatherConditions = weatherConditionService.getLastSixWeatherConditionsSortedByDate();

        for (WeatherCondition weatherCondition : weatherConditions) {
            System.out.println(weatherCondition.getmeasurementDate());

            kieSession.insert(weatherCondition);
            clock.advanceTime(1, TimeUnit.DAYS);
        }

        List<SoilMoisture> soilMoistureList = soilMoistureRepository.findAllByOrderByMeasurementDateAsc();

        for (SoilMoisture soilMoisture : soilMoistureList) {
            kieSession.insert(soilMoisture);

        }


        kieSession.fireAllRules();
        if (!recommendedPlantsForAlarms.getRecommendedPlants().isEmpty()) {
            recommendedPlantsForAlarms.setAlarmMessage("Drought detected based on the last 5 days of weather data.");
            recommendedPlantsForAlarms.setAlarmType(AlarmType.DROUGHT);
        }

        return recommendedPlantsForAlarms;

    }

    @Override
    public Set<RecommendedPlantDTO> recommendPlantsForSpecificClimates(AdviceRequestForTempDTO dto) {
        InputStream template = PlantAdviceService.class.getResourceAsStream("/rules/template/climate-classification.drt");
        InputStream data = PlantAdviceService.class.getResourceAsStream("/rules/template/climate-classification.xls");
        System.out.println("PRE JE: "+ dto.getClimates());

        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String drl = converter.compile(data, template, 3, 2);
        KieSession ksession = this.createKieSessionFromDRL(drl);
        ksession.insert(dto);
        ksession.fireAllRules();
        System.out.println(drl);
        ksession.dispose();

        System.out.println("SAD JE: "+ dto.getClimates());

        if (dto.getClimates().isEmpty()) {
            return new HashSet<>();
        }

        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        KieSession kieSession = kieContainer.newKieSession("kSession");
        kieSession.setGlobal("recommendations", recommendations);

        kieSession.insert(dto);
        List<Plant> plants = plantRepository.findAll();
        List<FloweringPlant> floweringPlants = floweringPlantRepository.findAll();

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
