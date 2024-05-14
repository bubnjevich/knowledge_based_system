package com.ftn.sbnz.model;

import com.thoughtworks.xstream.converters.time.LocalDateTimeConverter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.PropertyReactive;
import org.kie.api.definition.type.Role;

import java.io.Serializable;
import java.time.LocalDateTime;

@Role(Role.Type.EVENT)
@Expires("10m")
public class FlightEvent implements Serializable {

    private RunWay runWay;
    public FlightEvent() {
        super();
    }

    public FlightEvent(RunWay runWay) {
        this.runWay = runWay;
    }



    public RunWay getRunWay() {
        return runWay;
    }

    public void setRunWay(RunWay runWay) {
        this.runWay = runWay;
    }
}
