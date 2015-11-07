package org.jglr.dmx.attributes.io;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.EnumAttributeTypes;
import org.jglr.dmx.attributes.containers.*;
import org.jglr.dmx.element.Element;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import static org.jglr.dmx.utils.IOUtils.*;

public final class AttributeEncoders {

    /**
     * Writes nothing to the OutputStream, used for VOID and UNKNOWN types mainly
     */
    public static final AttributeEncoder nullEncoder = (model, out, value) -> {};

    public static void encodeElement(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Element.class);
        if(value == null) {
            writeLittleEndianInt(out, -1);
        } else {
            Element elem = (Element)value;
            if(elem.isStub()) {
                writeLittleEndianInt(out, -2);
                writeLittleEndianUUID(out, elem.getUuid());
            } else {
                writeLittleEndianInt(out, model.getElementList().indexOf(elem));
            }
        }
    }

    public static void encodeInt(Datamodel model, OutputStream out, Object value) throws IOException {
        checkMultiple(value, Integer.TYPE, Integer.class);
        Objects.requireNonNull(value);
        int number = (int) value;
        writeLittleEndianInt(out, number);
    }

    public static void encodeFloat(Datamodel model, OutputStream out, Object value) throws IOException {
        checkMultiple(value, Float.TYPE, Float.class);
        Objects.requireNonNull(value);
        float number = (float) value;
        writeLittleEndianFloat(out, number);
    }

    public static void encodeBool(Datamodel model, OutputStream out, Object value) throws IOException {
        checkMultiple(value, Boolean.TYPE, Boolean.class);
        Objects.requireNonNull(value);
        boolean bool = (boolean) value;
        writeByte(out, (byte) (bool ? 1 : 0));
    }

    public static void encodeString(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, String.class);
        Objects.requireNonNull(value);
        String str = (String) value;
        writeLittleEndianInt(out, model.getStrIndex(str));
    }

    public static void encodeColor(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Color.class);
        Objects.requireNonNull(value);
        Color col = (Color) value;
        writeByte(out, (byte) col.getRed());
        writeByte(out, (byte) col.getGreen());
        writeByte(out, (byte) col.getBlue());
        writeByte(out, (byte) col.getAlpha());
    }

    public static void encodeQuat(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Quaternion.class);
        Objects.requireNonNull(value);
        Quaternion v = (Quaternion) value;
        writeLittleEndianFloat(out, v.getX());
        writeLittleEndianFloat(out, v.getY());
        writeLittleEndianFloat(out, v.getZ());
        writeLittleEndianFloat(out, v.getW());
    }

    public static void encodeMat(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Matrix4.class);
        Objects.requireNonNull(value);
        Matrix4 v = (Matrix4) value;
        for(int i = 0;i<4*4;i++) {
            writeLittleEndianFloat(out, v.getRaw()[i]);
        }
    }

    public static void encodeVec2(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Vector2.class);
        Objects.requireNonNull(value);
        Vector2 v = (Vector2) value;
        writeLittleEndianFloat(out, v.getX());
        writeLittleEndianFloat(out, v.getY());
    }

    public static void encodeVec3(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Vector3.class);
        Objects.requireNonNull(value);
        Vector3 v = (Vector3) value;
        writeLittleEndianFloat(out, v.getX());
        writeLittleEndianFloat(out, v.getY());
        writeLittleEndianFloat(out, v.getZ());
    }

    public static void encodeAngle(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Angle.class);
        Objects.requireNonNull(value);
        Angle v = (Angle) value;
        writeLittleEndianFloat(out, v.getX());
        writeLittleEndianFloat(out, v.getY());
        writeLittleEndianFloat(out, v.getZ());
    }

    public static void encodeVec4(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Vector4.class);
        Objects.requireNonNull(value);
        Vector4 v = (Vector4) value;
        writeLittleEndianFloat(out, v.getX());
        writeLittleEndianFloat(out, v.getY());
        writeLittleEndianFloat(out, v.getZ());
        writeLittleEndianFloat(out, v.getW());
    }

    public static void encodeElementArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Element[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.ELEMENT, (Element[]) value);
    }

    public static void encodeAngleArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Angle[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.QANGLE, (Angle[]) value);
    }

    public static void encodeColorArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Color[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.COLOR, (Color[]) value);
    }

    public static void encodeMatrix4Array(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Matrix4[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.VMATRIX, (Matrix4[]) value);
    }

    public static void encodeQuaternionArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Quaternion[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.QUATERNION, (Quaternion[]) value);
    }

    public static void encodeVector2Array(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Vector2[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.VECTOR2, (Vector2[]) value);
    }

    public static void encodeVector3Array(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Vector3[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.VECTOR3, (Vector3[]) value);
    }

    public static void encodeVector4Array(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, Vector4[].class);
        Objects.requireNonNull(value);
        encodeArray(model, out, EnumAttributeTypes.VECTOR4, (Vector4[]) value);
    }

    public static void encodeStringArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, String[].class);
        Objects.requireNonNull(value);
        String[] arr = (String[]) value;
        writeLittleEndianInt(out, arr.length);
        for (String s : arr) {
            writeNullTerminatedString(out, s);
        }
    }

    public static void encodeBoolArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, boolean[].class);
        Objects.requireNonNull(value);
        // Sadly, primitives types arrays are not an instance of Object[]
        boolean[] array = (boolean[]) value;
        writeLittleEndianInt(out, array.length);
        for (boolean b : array) {
            writeByte(out, (byte) (b ? 1 : 0));
        }
    }

    public static void encodeFloatArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, float[].class);
        Objects.requireNonNull(value);
        // Sadly, primitives types arrays are not an instance of Object[]
        float[] array = (float[]) value;
        writeLittleEndianInt(out, array.length);
        for (float f : array) {
            writeLittleEndianFloat(out, f);
        }
    }

    public static void encodeIntArray(Datamodel model, OutputStream out, Object value) throws IOException {
        check(value, int[].class);
        Objects.requireNonNull(value);
        // Sadly, primitives types arrays are not an instance of Object[]
        int[] array = (int[]) value;
        writeLittleEndianInt(out, array.length);
        for (int i : array) {
            writeLittleEndianInt(out, i);
        }
    }

    private static void encodeArray(Datamodel model, OutputStream out, EnumAttributeTypes type, Object[] value) throws IOException {
        writeLittleEndianInt(out, value.length);
        for(Object v : value) {
            type.write(model, out, v);
        }
    }

    private static void check(Object value, Class<?> type) {
        if(value != null && !type.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("value is not of type "+type.getSimpleName());
    }

    private static void checkMultiple(Object value, Class<?>... types) {
        if(value != null) {
            String typesStr = "";
            for(Class<?> t : types) {
                if(t.isAssignableFrom(value.getClass())) {
                    return; // We found a matching type
                }
                typesStr += t.getSimpleName()+", ";
            }
            typesStr += "Void";
            throw new IllegalArgumentException("value is not of any of those types "+typesStr);
        }
    }
}
