package com.ftn.sbnz.service.tests;

import com.ftn.sbnz.model.*;
import com.ftn.sbnz.model.DTO.AdviceRequestDTO;
import com.ftn.sbnz.model.DTO.RecommendedPlantDTO;
import com.ftn.sbnz.model.enums.Climate;
import com.ftn.sbnz.model.enums.PlantFunctionality;
import com.ftn.sbnz.model.enums.PlantType;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.ObjectDataCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class RuleTemplatesTest {

	/**
     * Tests customer-classification-simple.drt template by manually creating
     * the corresponding DRL using a spreadsheet as the data source.
     */
    @Test
    public void testSimpleTemplateWithSpreadsheet2(){
        
        InputStream template = RuleTemplatesTest.class.getResourceAsStream("/rules/template/soil-classification.drt");
        InputStream data = RuleTemplatesTest.class.getResourceAsStream("/rules/template/soil-classification.xls");
        
        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String drl = converter.compile(data, template, 3, 2);
        
        System.out.println(drl);
        
        KieSession ksession = this.createKieSessionFromDRL(drl);
        
        this.doTest(ksession);
    }


	private void doTest(KieSession ksession){


        Soil soil1 = new Soil("LOAMY");
        Soil soil2 = new Soil("SANDY");
        Soil soil3 = new Soil("PEAT");
        ksession.insert(soil1);
        ksession.insert(soil2);

        Humidity humidity = new Humidity(30.0, 60.0);
        ksession.insert(humidity);

        List<Climate> suitableClimates = Arrays.asList(Climate.CONTINENTAL, Climate.TROPICAL);
        List<Soil> suitableSoilTypes = Arrays.asList(soil1, soil2);
        List<Soil> soils2 = List.of(soil3);
        List<PlantFunctionality> plantFunctionalities = Arrays.asList(PlantFunctionality.DECORATIVE, PlantFunctionality.DROUGHT_RESISTANCE);

        Plant plant = new Plant("Lavander", PlantType.FLOWER, suitableClimates, suitableSoilTypes,
                plantFunctionalities, humidity, 1.5, 8);
        ksession.insert(plant);
        ksession.insert(new Plant("Stonecrop", PlantType.FLOWER, suitableClimates, suitableSoilTypes,
                plantFunctionalities,  humidity, 1.5, 8));

        ksession.insert(new Plant("Rhododendron", PlantType.FLOWER, suitableClimates, soils2,
                plantFunctionalities, humidity, 1.5, 8));

        Set<RecommendedPlantDTO> recommendations = new HashSet<>();
        ksession.setGlobal("recommendations", recommendations);

        AdviceRequestDTO adviceRequestDTO = new AdviceRequestDTO();
        adviceRequestDTO.setSoilPh(4.6);
        ksession.insert(adviceRequestDTO);

        ksession.fireAllRules();
        for (RecommendedPlantDTO rec : recommendations) {
            System.out.println("Recommended Plant: " + rec);
        }


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
}
