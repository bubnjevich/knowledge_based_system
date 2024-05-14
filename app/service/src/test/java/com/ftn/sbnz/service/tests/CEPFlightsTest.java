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
        SessionPseudoClock clock = ksession.getSessionClock();
        RunWay runWay = new RunWay("My Run Way 1");
        for (int index = 0; index < 100; index++) {
            ksession.insert(new FlightEvent(runWay));
            clock.advanceTime(4, TimeUnit.MINUTES);
            ksession.insert(new FlightEvent(runWay));
            int ruleCount = ksession.fireAllRules();
            assertThat(ruleCount, equalTo(0));
            clock.advanceTime(4, TimeUnit.MINUTES);

        }
        //We manually advance time 1 minute, without a heart beat
        ksession.insert(new FlightEvent(runWay));
        ksession.insert(new FlightEvent(runWay));
        int ruleCount = ksession.fireAllRules();
        assertThat(ruleCount, equalTo(1));

    }
}
