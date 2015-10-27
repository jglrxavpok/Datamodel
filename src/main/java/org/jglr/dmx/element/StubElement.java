package org.jglr.dmx.element;

import org.jglr.dmx.Datamodel;

import java.util.UUID;

public class StubElement extends Element {
    public StubElement(Datamodel owner, UUID uuid) {
        super(owner, "Stub element", "DmElement", uuid, true);
    }
}
