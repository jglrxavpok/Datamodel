package org.jglr.dmx;

import org.jglr.dmx.attrvalues.AttributeValue;
import org.jglr.dmx.attrvalues.NullAttributeValue;

public class Attribute {

    private final String name;
    private final AttributeList owner;
    private final AttributeValue value;

    public Attribute(String name, AttributeList owner) {
        this(name, new NullAttributeValue(), owner);
    }

    public Attribute(String name, AttributeValue value, AttributeList owner) {
        this.name = name;
        this.value = value;
        this.owner = owner;
    }

    public AttributeList getOwner() {
        return owner;
    }

    public AttributeValue getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
