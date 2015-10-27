package org.jglr.dmx.attributes;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.containers.*;
import org.jglr.dmx.element.Element;

import java.io.DataInputStream;
import java.io.Reader;
import java.util.function.BiFunction;

import static org.jglr.dmx.utils.IOUtils.*;

public enum EnumAttributeTypes {
    UNKNOWN(0, Void.class, (datamodel, in) -> null),
    FIRST_VALUE_TYPE(1, Void.class, (datamodel, in) -> null),
    ELEMENT(FIRST_VALUE_TYPE.value(), Element.class, AttributeExtraction::extractElement),
    INT(2, Integer.TYPE, AttributeExtraction::extractInt),
    FLOAT(3, Float.TYPE, AttributeExtraction::extractFloat),
    BOOL(4, Boolean.class, AttributeExtraction::extractBool),
    STRING(5, String.class, AttributeExtraction::extractString),
    VOID(6, Void.class, (datamodel, in) -> null),
    TIME(7, Float.TYPE, AttributeExtraction::extractFloat),
    COLOR(8, Color.class, AttributeExtraction::extractColor), //rgba
    VECTOR2(9, Vector2.class, AttributeExtraction::extractVec2),
    VECTOR3(10, Vector3.class, AttributeExtraction::extractVec3),
    VECTOR4(11, Vector4.class, AttributeExtraction::extractVec4),
    QANGLE(12, Angle.class, AttributeExtraction::extractAngle),
    QUATERNION(13, Quaternion.class, AttributeExtraction::extractQuat),
    VMATRIX(14, Matrix4.class, AttributeExtraction::extractMat),

    FIRST_ARRAY_TYPE(15, Void.class, (datamodel, dataInputStream) -> null),

    ELEMENT_ARRAY(FIRST_ARRAY_TYPE.value(), Element[].class, AttributeExtraction::extractElementArray),
    INT_ARRAY(16, int[].class, AttributeExtraction::extractIntArray),
    FLOAT_ARRAY(17, float[].class, AttributeExtraction::extractFloatArray),
    BOOL_ARRAY(18, boolean[].class, AttributeExtraction::extractBoolArray),
    STRING_ARRAY(19, String[].class, AttributeExtraction::extractStringArray),
    VOID_ARRAY(20, Void[].class, (datamodel, dataInputStream) -> new Void[0]),
    TIME_ARRAY(21, Float[].class, AttributeExtraction::extractFloatArray),
    COLOR_ARRAY(22, Color[].class, AttributeExtraction::extractColorArray),
    VECTOR2_ARRAY(23, Vector2[].class, AttributeExtraction::extractVector2Array),
    VECTOR3_ARRAY(24, Vector3[].class, AttributeExtraction::extractVector3Array),
    VECTOR4_ARRAY(25, Vector4[].class, AttributeExtraction::extractVector4Array),
    QANGLE_ARRAY(26, Angle[].class, AttributeExtraction::extractAngleArray),
    QUATERNION_ARRAY(27, Quaternion[].class, AttributeExtraction::extractQuaternionArray),
    VMATRIX_ARRAY(28, Matrix4[].class, AttributeExtraction::extractMatrix4Array);


    public static final int TYPE_COUNT = 29;

    private int value;
    private BiFunction<Datamodel, Reader, Object> func;
    private Class<?> type;

    EnumAttributeTypes(int value, Class<?> type, BiFunction<Datamodel, Reader, Object> func) {
        this.value = value;
        this.type = type;
        this.func = func;
    }

    public int value() {
        return value;
    }

    public static EnumAttributeTypes getType(int id) {
        for(EnumAttributeTypes t : values()) {
            if(t != FIRST_ARRAY_TYPE && t != FIRST_VALUE_TYPE)
                if(t.value == (int)id)
                    return t;
        }
        return UNKNOWN;
    }

    public Class<?> type() {
        return type;
    }

    public AttributeValue handle(Datamodel datamodel, Reader in) {
        return new AttributeValue(type, func.apply(datamodel, in));
    }
}
