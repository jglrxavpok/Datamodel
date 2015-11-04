package org.jglr.dmx.attributes;

/**
 * Attribute value with a null value and Void type.
 */
public class NullAttributeValue extends AttributeValue {

    public NullAttributeValue() {
        super(EnumAttributeTypes.VOID, null);
    }
}
