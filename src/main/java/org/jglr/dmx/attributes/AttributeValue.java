package org.jglr.dmx.attributes;

/**
 * The value of an attribute, comes bundled with a type (often a {@link Class} instance)
 */
public class AttributeValue {

    private final EnumAttributeTypes type;
    private final Object value;

    public AttributeValue(EnumAttributeTypes type, Object value) {
        this.type = type;
        if(value == null && type != EnumAttributeTypes.VOID) {
            throw new NullPointerException("value");
        } else if(type == EnumAttributeTypes.UNKNOWN) {
            this.value = value; // We allow anything to be put here as we don't know the type
        }
        else if(value != null && !type.internalType().isAssignableFrom(value.getClass())) {
            if(!type.internalType().isPrimitive()) {
                throw new IllegalArgumentException("value is of type "+value.getClass()+" while internal type is "+type.internalType());
            } else {
                try {
                    this.value = value;
                    // TODO: actually check for type
                } catch (Exception e) {
                    throw new IllegalArgumentException("value is of type "+value.getClass()+" while internal type is "+type.internalType()+" (tried casting)", e);
                }
            }
        } else {
            this.value = value;
        }
    }

    public EnumAttributeTypes getType() {
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
