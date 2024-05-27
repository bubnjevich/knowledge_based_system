package com.ftn.sbnz.service.tests;


import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.enums.Climate;
import com.ftn.sbnz.model.enums.PlantFunctionality;
import com.ftn.sbnz.model.enums.PlantType;
import com.ftn.sbnz.service.repositories.PlantRepository;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PlantRepositoryTest {


    @Test
    public void testCepRules() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("cepConfigKsessionPseudoClock");
        fillSession(ksession);
          runPseudoClockExample(ksession);
    }

    private void fillSession(KieSession ksession) {

        Soil soil1 = new Soil("LOAMY");
        Soil soil2 = new Soil("SANDY");
        ksession.insert(soil1);
        ksession.insert(soil2);

        Humidity humidity = new Humidity(30.0, 60.0);
        ksession.insert(humidity);

        List<Climate> suitableClimates = Arrays.asList(Climate.CONTINENTAL, Climate.TROPICAL);
        List<Soil> suitableSoilTypes = Arrays.asList(soil1, soil2);
        List<PlantFunctionality> plantFunctionalities = Arrays.asList(PlantFunctionality.DECORATIVE, PlantFunctionality.DROUGHT_RESISTANCE);

        Plant plant = new Plant("Lavander", PlantType.FLOWER, suitableClimates, suitableSoilTypes,
                plantFunctionalities, humidity, 1.5, 8);
        ksession.insert(plant);
        ksession.insert(new Plant("Stonecrop", PlantType.FLOWER, suitableClimates, suitableSoilTypes,
                plantFunctionalities,  humidity, 1.5, 8));


    }

    private void runPseudoClockExample(KieSession ksession) {
        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        ksession.setGlobal("recommendations", recommendations);


        SessionPseudoClock clock = ksession.getSessionClock();

        // Insert weather conditions
        ksession.insert(new WeatherCondition("2024-05-15", 30.0, 1.0)); // Day 1
        clock.advanceTime(1, TimeUnit.DAYS);
        ksession.insert(new WeatherCondition("2024-05-16", 31.0, 2.0)); // Day 2
        clock.advanceTime(1, TimeUnit.DAYS);
        ksession.insert(new WeatherCondition("2024-05-17", 29.0, 0.5)); // Day 3
        clock.advanceTime(1, TimeUnit.DAYS);
        ksession.insert(new WeatherCondition("2024-05-18", 32.0, 1.5)); // Day 4
        clock.advanceTime(1, TimeUnit.DAYS);
        ksession.insert(new WeatherCondition("2024-05-19", 33.0, 0.5)); // Day 5
        clock.advanceTime(1, TimeUnit.DAYS);

        ksession.insert(new SoilMoisture("2024-05-19",18.0));
        clock.advanceTime(1, TimeUnit.HOURS);

        ksession.fireAllRules();

        assertTrue(recommendations.size() > 0);
        for (RecommendedPlantDTO rec : recommendations) {
            System.out.println("Recommended Plant: " + rec.getName());
        }

        ksession.dispose();
    }


}
