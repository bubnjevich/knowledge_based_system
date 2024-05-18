package com.ftn.sbnz.service.tests;


import com.ftn.sbnz.model.*;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CEPFlightsTest {

    @Test
    public void testCepRules() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession ksession = kc.newKieSession("cepConfigKsessionPseudoClock");
          runPseudoClockExample(ksession);
    }

    private void runPseudoClockExample(KieSession ksession) {


    }
}
