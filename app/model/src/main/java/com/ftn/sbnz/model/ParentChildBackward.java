package com.ftn.sbnz.model;

import org.kie.api.definition.type.Position;

public class ParentChildBackward

{
    @Position(0)
    public String child;
    @Position(1)
    public String parent;
    public String getChild() {
        return child;
    }
    public void setChild(String child) {
        this.child = child;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }

    public ParentChildBackward(String child, String parent) {
        this.child = child;
        this.parent = parent;
    }

    public ParentChildBackward() {
    }
}
