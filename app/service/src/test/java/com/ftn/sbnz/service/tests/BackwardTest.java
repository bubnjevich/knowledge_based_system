package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.enums.Climate;
import com.ftn.sbnz.model.enums.FlowerColor;
import com.ftn.sbnz.model.enums.PlantType;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaGroup;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import java.awt.*;
import java.util.*;

public class BackwardTest {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("backwardSession");
//        AgendaGroup agendaGroup = kSession.getAgenda().getAgendaGroup("level1");
//        agendaGroup.setFocus();
        Set<RecommendedPlantDTO> recommended = new HashSet<>();
        kSession.setGlobal("recommendations", recommended);

        kSession.insert(new Plant("Rose", PlantType.FLOWER, Arrays.asList(Climate.CONTINENTAL), null, null, null, 0.0, 1));
        kSession.insert(new Plant("Oak", PlantType.TREE, Arrays.asList(Climate.CONTINENTAL),null, null, null, 0.0, 0));
        kSession.insert(new Plant("Fern", PlantType.FLOWER, Arrays.asList(Climate.TROPICAL), null, null, null, 0.0, 0));
        kSession.insert(new FloweringPlant("Tulipa", PlantType.FLOWER, Arrays.asList(Climate.CONTINENTAL), null, null, 0.0, 0.0, null, 0.5, 0));
        kSession.insert(new PerennialPlant("Jasmin", PlantType.FLOWER, Arrays.asList(Climate.CONTINENTAL), null, null, 0.0, 0.0, null, 0.5, 0));

        kSession.insert(new AnnualPlant("Dracena", PlantType.FLOWER, Arrays.asList(Climate.TROPICAL), null, null, null, 0.5, null, null, 0));
        kSession.insert(new AnnualPlant("Ivy Glacier", PlantType.FLOWER, Arrays.asList(Climate.TROPICAL), null, null, null, 0.5, null, null, 0));
        kSession.insert(new AnnualPlant("Maslacak", PlantType.FLOWER, Arrays.asList(Climate.TROPICAL), null, null, null, 0.5, null, FlowerColor.YELLOW, 0));
        kSession.insert(new Plant("Bokvica", PlantType.FLOWER, Arrays.asList(Climate.TROPICAL), null, null, null, 0.5,  0));


        kSession.insert(new ParentChildBackward("Bokvica", "AnnualNotFloweringPlant"));
        kSession.insert(new ParentChildBackward("FloweringPlant", "PerennialPlant"));
        kSession.insert(new ParentChildBackward("PerennialPlant", "Plant"));
        kSession.insert(new ParentChildBackward("AnnualFloweringPlant", "Plant"));
        kSession.insert(new ParentChildBackward("AnnualNotFloweringPlant", "Plant"));


        // Izvršite upit za pronalaženje biljaka sa sličnim karakteristikama
        kSession.fireAllRules();
        for (RecommendedPlantDTO dto: recommended) {
            System.out.println(dto.getName());
        }
        kSession.dispose();
    }
}
