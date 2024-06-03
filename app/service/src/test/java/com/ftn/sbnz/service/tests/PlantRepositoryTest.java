package com.ftn.sbnz.service.tests;


import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantsForAlarms;
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
        RecommendedPlantsForAlarms r = new RecommendedPlantsForAlarms();
        ksession.setGlobal("recommendationsForAlarms", r);


        // Insert weather conditions
        ksession.insert(new WeatherCondition("2024-06-27", 30.0, 1.0)); // Day 1
        ksession.insert(new WeatherCondition("2024-06-28", 31.0, 2.0)); // Day 2
        ksession.insert(new WeatherCondition("2024-06-29", 29.0, 0.5)); // Day 3
        ksession.insert(new WeatherCondition("2024-06-30", 32.0, 1.5)); // Day 4
        ksession.insert(new WeatherCondition("2024-07-01", 33.0, 0.5)); // Day 5


        ksession.insert(new SoilMoisture("2024-06-02",18.0));

        ksession.fireAllRules();

        assertTrue(recommendations.size() > 0);
        for (RecommendedPlantDTO rec : recommendations) {
            System.out.println("Recommended Plant: " + rec.getName());
        }

        ksession.dispose();
    }


}
