package org.jglr.dmx.attributes;

import org.jglr.dmx.Datamodel;

import java.util.ArrayList;

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
