package org.jglr.dmx;

import java.util.ArrayList;

public class ElementList extends ArrayList<Element> {

    private final Datamodel owner;

    public ElementList(Datamodel owner) {
        super();
        this.owner = owner;
    }

    public Datamodel getOwner() {
        return owner;
    }
}
