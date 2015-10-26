package org.jglr.dmx.attrvalues;

import java.lang.reflect.Type;

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
}
