package org.jglr.dmx;

import java.util.ArrayList;
import java.util.LinkedList;

public class AttributeList extends ArrayList<Attribute> {

    private final Datamodel owner;

    public AttributeList(Datamodel owner) {
        super();
        this.owner = owner;
    }

    public Datamodel getOwner() {
        return owner;
    }
}
