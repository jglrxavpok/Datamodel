package org.jglr.dmx.attributes;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.containers.*;
import org.jglr.dmx.element.Element;

import java.io.InputStream;
import java.util.function.BiFunction;

/**
 * List of all possible types for an {@link Attribute}
 */
public enum EnumAttributeTypes {
    UNKNOWN(0, Void.TYPE, (datamodel, in) -> null, false),
    FIRST_VALUE_TYPE(1, Void.TYPE, (datamodel, in) -> null, false),
    ELEMENT(FIRST_VALUE_TYPE.value(), Element.class, AttributeExtraction::extractElement, false),
    INT(2, Integer.TYPE, AttributeExtraction::extractInt, false),
    FLOAT(3, Float.TYPE, AttributeExtraction::extractFloat, false),
    BOOL(4, Boolean.TYPE, AttributeExtraction::extractBool, false),
    STRING(5, String.class, AttributeExtraction::extractString, false),
    VOID(6, Void.TYPE, (datamodel, in) -> null, false),
    TIME(7, Float.TYPE, AttributeExtraction::extractFloat, false),
    COLOR(8, Color.class, AttributeExtraction::extractColor, false), //rgba
    VECTOR2(9, Vector2.class, AttributeExtraction::extractVec2, false),
    VECTOR3(10, Vector3.class, AttributeExtraction::extractVec3, false),
    VECTOR4(11, Vector4.class, AttributeExtraction::extractVec4, false),
    QANGLE(12, Angle.class, AttributeExtraction::extractAngle, false),
    QUATERNION(13, Quaternion.class, AttributeExtraction::extractQuat, false),
    VMATRIX(14, Matrix4.class, AttributeExtraction::extractMat, false),

    FIRST_ARRAY_TYPE(15, Void.TYPE, (datamodel, dataInputStream) -> null, true),

    ELEMENT_ARRAY(FIRST_ARRAY_TYPE.value(), Element[].class, AttributeExtraction::extractElementArray, true),
    INT_ARRAY(16, int[].class, AttributeExtraction::extractIntArray, true),
    FLOAT_ARRAY(17, float[].class, AttributeExtraction::extractFloatArray, true),
    BOOL_ARRAY(18, boolean[].class, AttributeExtraction::extractBoolArray, true),
    STRING_ARRAY(19, String[].class, AttributeExtraction::extractStringArray, true),
    VOID_ARRAY(20, Void[].class, (datamodel, dataInputStream) -> new Void[0], true),
    TIME_ARRAY(21, float[].class, AttributeExtraction::extractFloatArray, true),
    COLOR_ARRAY(22, Color[].class, AttributeExtraction::extractColorArray, true),
    VECTOR2_ARRAY(23, Vector2[].class, AttributeExtraction::extractVector2Array, true),
    VECTOR3_ARRAY(24, Vector3[].class, AttributeExtraction::extractVector3Array, true),
    VECTOR4_ARRAY(25, Vector4[].class, AttributeExtraction::extractVector4Array, true),
    QANGLE_ARRAY(26, Angle[].class, AttributeExtraction::extractAngleArray, true),
    QUATERNION_ARRAY(27, Quaternion[].class, AttributeExtraction::extractQuaternionArray, true),
    VMATRIX_ARRAY(28, Matrix4[].class, AttributeExtraction::extractMatrix4Array, true);


    public static final int TYPE_COUNT = 29;

    private int value;
    private BiFunction<Datamodel, InputStream, Object> extractor;
    private Class<?> internalType;
    private boolean array;

    EnumAttributeTypes(int value, Class<?> internalType, BiFunction<Datamodel, InputStream, Object> extractor, boolean array) {
        this.value = value;
        this.internalType = internalType;
        this.extractor = extractor;
        this.array = array;
    }

    public int value() {
        return value;
    }

    public static EnumAttributeTypes getType(int id) {
        for(EnumAttributeTypes t : values()) {
            if(t != FIRST_ARRAY_TYPE && t != FIRST_VALUE_TYPE && t.value == id)
                return t;
        }
        return UNKNOWN;
    }

    public Class<?> internalType() {
        return internalType;
    }

    /**
     * Extracts a value from the reader
     */
    public AttributeValue extract(Datamodel datamodel, InputStream in) {
        return new AttributeValue(this, extractor.apply(datamodel, in));
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public static EnumAttributeTypes deduceType(Object value) {
        if(value == null)
            return VOID;
        for(EnumAttributeTypes t : values()) {
            if(t.internalType().isAssignableFrom(value.getClass())) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
