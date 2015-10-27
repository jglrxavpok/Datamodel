package org.jglr.dmx.attributes;

import java.lang.reflect.Type;

/**
 * The value of an attribute, comes bundled with a type (often a {@link Class} instance)
 */
public class AttributeValue {

    private final Type type;
    private final Object value;

    public AttributeValue(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
