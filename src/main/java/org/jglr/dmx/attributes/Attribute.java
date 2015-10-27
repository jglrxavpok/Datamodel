package org.jglr.dmx.attributes;

/**
 * An attribute is a property of an {@link org.jglr.dmx.element.Element Element}. It has a name, a type and a value.
 *
 * The type is actually embedded in the {@link Attribute#value value field}.
 */
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
