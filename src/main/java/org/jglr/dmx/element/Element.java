package org.jglr.dmx.element;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.Attribute;
import org.jglr.dmx.attributes.AttributeList;
import org.jglr.dmx.attributes.AttributeValue;
import org.jglr.dmx.attributes.EnumAttributeTypes;

import java.util.UUID;
import javax.annotation.Nullable;

public class Element extends AttributeList {

    private final Datamodel owner;
    private final String name;
    private final String className;
    private final UUID uuid;
    private final boolean stub;

    public Element(Datamodel owner) {
        this(owner, "StubElement", (UUID)null);
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

    public Attribute createAttribute(String name) {
        Attribute attr = new Attribute(name, this);
        add(attr);
        return attr;
    }

    public Attribute createAttribute(String name, EnumAttributeTypes type, Object value) {
        Attribute attr = new Attribute(name, new AttributeValue(type, value), this);
        add(attr);
        return new Attribute(name, new AttributeValue(type, value), this);
    }

    public Attribute createAttribute(String name, AttributeValue value) {
        Attribute attr = new Attribute(name, value, this);
        add(attr);
        return attr;
    }

    public Attribute createAttribute(String name, Object value) {
        Attribute attr = new Attribute(name, new AttributeValue(EnumAttributeTypes.deduceType(value), value), this);
        add(attr);
        return attr;
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

    public Element copyWithOwner(Datamodel newOwner) {
        return new Element(newOwner, name, className, uuid, stub);
    }

    @Override
    public String toString() {
        return "Element<"+className+"> ("+name+", "+uuid+")";
    }
}
