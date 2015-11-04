package org.jglr.dmx.element;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.AttributeList;

import java.util.UUID;
import javax.annotation.Nullable;

public class Element extends AttributeList {

    private final Datamodel owner;
    private final String name;
    private final String className;
    private final UUID uuid;
    private final boolean stub;

    public Element(Datamodel owner) {
        this(owner, "Stub element", (UUID)null);
    }

    public Element(Datamodel owner, String name) {
        this(owner, "Stub element", "DmElement", null);
    }

    public Element(Datamodel owner, String name, String className) {
        this(owner, name, className, null);
    }

    public Element(Datamodel owner, String name, @Nullable UUID uuid) {
        this(owner, name, "DmElement", uuid);
    }

    public Element(Datamodel owner, String name, String className, @Nullable UUID uuid) {
        this(owner, name, className, uuid, false);
    }

    public Element(Datamodel owner, String name, String className, @Nullable UUID uuid, boolean stub) {
        super(owner);
        this.owner = owner;
        this.name = name;
        this.className = className;
        if(uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
        }
        this.stub = stub;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public Datamodel getOwner() {
        return owner;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isStub() {
        return stub;
    }

    @Override
    public String toString() {
        return "Element<"+className+"> ("+owner+", "+name+", "+uuid+")";
    }
}
