package org.jglr.dmx.attributes;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.containers.*;
import org.jglr.dmx.attributes.io.AttributeEncoder;
import org.jglr.dmx.attributes.io.AttributeEncoders;
import org.jglr.dmx.attributes.io.AttributeExtraction;
import org.jglr.dmx.element.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.BiFunction;

/**
 * List of all possible types for an {@link Attribute}
 */
public enum EnumAttributeTypes {
    UNKNOWN(0, Void.TYPE, (datamodel, in) -> null, AttributeEncoders.nullEncoder, false),
    FIRST_VALUE_TYPE(1, Void.TYPE, (datamodel, in) -> null, AttributeEncoders.nullEncoder, false),
    ELEMENT(FIRST_VALUE_TYPE.value(), Element.class, AttributeExtraction::extractElement, AttributeEncoders::encodeElement, false),
    INT(2, Integer.TYPE, AttributeExtraction::extractInt, AttributeEncoders::encodeInt, false),
    FLOAT(3, Float.TYPE, AttributeExtraction::extractFloat, AttributeEncoders::encodeFloat, false),
    BOOL(4, Boolean.TYPE, AttributeExtraction::extractBool, AttributeEncoders::encodeBool, false),
    STRING(5, String.class, AttributeExtraction::extractString, AttributeEncoders::encodeString, false),
    VOID(6, Void.TYPE, (datamodel, in) -> null, AttributeEncoders.nullEncoder, false),
    TIME(7, Float.TYPE, AttributeExtraction::extractFloat, AttributeEncoders::encodeFloat, false),
    COLOR(8, Color.class, AttributeExtraction::extractColor, AttributeEncoders::encodeColor, false), //rgba
    VECTOR2(9, Vector2.class, AttributeExtraction::extractVec2, AttributeEncoders::encodeVec2, false),
    VECTOR3(10, Vector3.class, AttributeExtraction::extractVec3, AttributeEncoders::encodeVec3, false),
    VECTOR4(11, Vector4.class, AttributeExtraction::extractVec4, AttributeEncoders::encodeVec4, false),
    QANGLE(12, Angle.class, AttributeExtraction::extractAngle, AttributeEncoders::encodeAngle, false),
    QUATERNION(13, Quaternion.class, AttributeExtraction::extractQuat, AttributeEncoders::encodeQuat, false),
    VMATRIX(14, Matrix4.class, AttributeExtraction::extractMat, AttributeEncoders::encodeMat, false),

    FIRST_ARRAY_TYPE(15, Void.TYPE, (datamodel, dataInputStream) -> null, AttributeEncoders.nullEncoder,true),

    ELEMENT_ARRAY(FIRST_ARRAY_TYPE.value(), Element[].class, AttributeExtraction::extractElementArray, AttributeEncoders::encodeElementArray, true),
    INT_ARRAY(16, int[].class, AttributeExtraction::extractIntArray, AttributeEncoders::encodeIntArray, true),
    FLOAT_ARRAY(17, float[].class, AttributeExtraction::extractFloatArray, AttributeEncoders::encodeFloatArray, true),
    BOOL_ARRAY(18, boolean[].class, AttributeExtraction::extractBoolArray, AttributeEncoders::encodeBoolArray, true),
    STRING_ARRAY(19, String[].class, AttributeExtraction::extractStringArray, AttributeEncoders::encodeStringArray, true),
    VOID_ARRAY(20, Void[].class, (datamodel, dataInputStream) -> new Void[0], AttributeEncoders.nullEncoder, true),
    TIME_ARRAY(21, float[].class, AttributeExtraction::extractFloatArray, AttributeEncoders::encodeFloatArray, true),
    COLOR_ARRAY(22, Color[].class, AttributeExtraction::extractColorArray, AttributeEncoders::encodeColorArray, true),
    VECTOR2_ARRAY(23, Vector2[].class, AttributeExtraction::extractVector2Array, AttributeEncoders::encodeVector2Array, true),
    VECTOR3_ARRAY(24, Vector3[].class, AttributeExtraction::extractVector3Array, AttributeEncoders::encodeVector3Array, true),
    VECTOR4_ARRAY(25, Vector4[].class, AttributeExtraction::extractVector4Array, AttributeEncoders::encodeVector4Array, true),
    QANGLE_ARRAY(26, Angle[].class, AttributeExtraction::extractAngleArray, AttributeEncoders::encodeAngleArray, true),
    QUATERNION_ARRAY(27, Quaternion[].class, AttributeExtraction::extractQuaternionArray, AttributeEncoders::encodeQuaternionArray, true),
    VMATRIX_ARRAY(28, Matrix4[].class, AttributeExtraction::extractMatrix4Array, AttributeEncoders::encodeMatrix4Array, true);


    public static final int TYPE_COUNT = 29;
    private final AttributeEncoder encoder;
    private int value;
    private BiFunction<Datamodel, InputStream, Object> extractor;
    private Class<?> internalType;
    private boolean array;

    EnumAttributeTypes(int value, Class<?> internalType, BiFunction<Datamodel, InputStream, Object> extractor, AttributeEncoder encoder, boolean array) {
        this.value = value;
        this.internalType = internalType;
        this.extractor = extractor;
        this.encoder = encoder;
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

    public void write(Datamodel model, OutputStream out, Object value) throws IOException {
        encoder.encode(model, out, value);
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
        // Check primitives
        if(value instanceof Float) {
            return FLOAT;
        }
        if(value instanceof Integer) {
            return INT;
        }
        if(value instanceof Boolean) {
            return BOOL;
        }
        for(EnumAttributeTypes t : values()) {
            if(t.internalType().isAssignableFrom(value.getClass())) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
