package org.jglr.dmx.attributes;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.containers.*;
import org.jglr.dmx.element.Element;
import org.jglr.dmx.element.StubElement;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.jglr.dmx.utils.IOUtils.*;

/**
 * Utils class used to extract attribute values from a Reader. Due to its use in {@link EnumAttributeTypes} constructors,
 * it returns default values if an {@link IOException} happens.
 */
public final class AttributeExtraction {

    /**
     * Extracts an {@link Element} from the stream represented by the Reader.<br/>
     * Starts by reading an int.<br/>
     * If this int
     * <ul>
     *     <li>is equal to -1, the element does not exist, the method returns null</lu>
     *     <li>is equal to -2, the element existed but is not in the file, a stub element is therefore generated</lu>
     *     <li>a positive number, the element is the one with an index equal to this int in the element list of the datamodel</lu>
     * </ul>
     * @param model
     *             The datamodel currently being loaded
     * @param in
     *          The Reader from which to extract the value
     * @return
     *      An Element instance, or null if couldn't read one.
     */
    public static Element extractElement(Datamodel model, InputStream in) {
        try {
            int id = readLittleEndianInt(in);
            if(id == -1) {
                return null;
            } else if(id == -2) {
                return new StubElement(model, UUID.nameUUIDFromBytes(readLittleEndianUUID(in)));
            } else {
                return model.getElementList().get(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads an int from the stream. It seems ints are stored the little-endian way by Blender (or part of the DMX format?).
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        An int
     */
    public static int extractInt(Datamodel model, InputStream in) {
        try {
            return readLittleEndianInt(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Reads a float from the stream.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A float
     */
    public static float extractFloat(Datamodel model, InputStream in) {
        try {
            return readFloat(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0f;
    }

    /**
     * Reads a boolean from the stream. A boolean actually takes a whole byte instead of a bit.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A boolean
     */
    public static boolean extractBool(Datamodel model, InputStream in) {
        try {
            return readBool(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reads a char (from the C language specification) from the stream.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A C-char, actually just a byte
     */
    public static int extractCChar(Datamodel model, InputStream in) {
        try {
            return readByte(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Reads a String instance from the stream. An index is first read and the String is then fetched from the string directory of the datamodel.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A String instance
     */
    public static String extractString(Datamodel model, InputStream in) {
        try {
            int index = readLittleEndianInt(in);
            return model.getDictionary().get(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads a color from the stream. A color is written as 4 tightly-packed floats in the RGBA format.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A color
     */
    public static Color extractColor(Datamodel model, InputStream in) {
        return new Color(extractCChar(model, in), extractCChar(model, in), extractCChar(model, in), extractCChar(model, in));
    }

    /**
     * Reads a 2 dimensional vector from the stream. A 2 dimensional vector is written as 2 tightly-packed floats for each of the components.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A 2 dimensional vector
     */
    public static Vector2 extractVec2(Datamodel model, InputStream in) {
        return new Vector2(readFloatVector(in, 2));
    }

    /**
     * Reads a 3 dimensional vector from the stream. A 3 dimensional vector is written as 3 tightly-packed floats for each of the components.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A 3 dimensional vector
     */
    public static Vector3 extractVec3(Datamodel model, InputStream in) {
        return new Vector3(readFloatVector(in, 3));
    }

    /**
     * Reads a pitch/yaw/roll angle from the stream. An angle is written as 3 tightly-packed floats for each of the components.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A pitch/yaw/roll angle
     */
    public static Angle extractAngle(Datamodel model, InputStream in) {
        return new Angle(readFloatVector(in, 3));
    }

    /**
     * Reads a 4 dimensional vector from the stream. A 4 dimensional vector is written as 4 tightly-packed floats for each of the components.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A 4 dimensional vector
     */
    public static Vector4 extractVec4(Datamodel model, InputStream in) {
        return new Vector4(readFloatVector(in, 4));
    }

    /**
     * Reads a quaternion from the stream. A quaternion is written as 4 tightly-packed floats for each of the components.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A 3 dimensional vector
     */
    public static Quaternion extractQuat(Datamodel model, InputStream in) {
        return new Quaternion(readFloatVector(in, 4));
    }

    /**
     * Reads a 4x4 matrix from the stream. A 4x4 matrix is written as 16 tightly-packed floats for each of the components.
     * @param model
     *              The datamodel currently being loaded
     * @param in
     *          The reader from which to extract the value
     * @return
     *        A 4x4 matrix
     */
    public static Matrix4 extractMat(Datamodel model, InputStream in) {
        return new Matrix4(readFloatVector(in, 4*4));
    }

    private static float[] readFloatVector(InputStream in, int dimensions) {
        float[] res = new float[dimensions];
        for(int i = 0;i<dimensions;i++) {
            try {
                res[i] = readFloat(in);
            } catch (IOException e) {
                e.printStackTrace();
                res[i] = 0f;
            }
        }
        return res;
    }

    // Start of attribute arrays

    /**
     * Extracts an array of Elements from the reader
     */
    public static Element[] extractElementArray(Datamodel model, InputStream in) {
        try {
            Element[] arr = new Element[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.ELEMENT, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of ints from the reader
     */
    public static int[] extractIntArray(Datamodel model, InputStream in) {
        try {
            Integer[] arr = new Integer[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.INT, arr);

            int[] result = new int[arr.length];
            for(int i = 0;i<arr.length;i++) {
                result[i] = arr[i];
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of floats from the reader
     */
    public static float[] extractFloatArray(Datamodel model, InputStream in) {
        try {
            Float[] arr = new Float[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.FLOAT, arr);

            float[] result = new float[arr.length];
            for(int i = 0;i<arr.length;i++) {
                result[i] = arr[i];
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of booleans from the reader
     */
    public static boolean[] extractBoolArray(Datamodel model, InputStream in) {
        try {
            Boolean[] arr = new Boolean[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.BOOL, arr);

            boolean[] result = new boolean[arr.length];
            for(int i = 0;i<arr.length;i++) {
                result[i] = arr[i];
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Colors from the reader
     */
    public static Color[] extractColorArray(Datamodel model, InputStream in) {
        try {
            Color[] arr = new Color[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.COLOR, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Vector2 from the reader
     */
    public static Vector2[] extractVector2Array(Datamodel model, InputStream in) {
        try {
            Vector2[] arr = new Vector2[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VECTOR2, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Vector3 from the reader
     */
    public static Vector3[] extractVector3Array(Datamodel model, InputStream in) {
        try {
            Vector3[] arr = new Vector3[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VECTOR3, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Vector4 from the reader
     */
    public static Vector4[] extractVector4Array(Datamodel model, InputStream in) {
        try {
            Vector4[] arr = new Vector4[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VECTOR4, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Quaternion from the reader
     */
    public static Quaternion[] extractQuaternionArray(Datamodel model, InputStream in) {
        try {
            Quaternion[] arr = new Quaternion[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.QUATERNION, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Matrix from the reader
     */
    public static Matrix4[] extractMatrix4Array(Datamodel model, InputStream in) {
        try {
            Matrix4[] arr = new Matrix4[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VMATRIX, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of Angle from the reader
     */
    public static Angle[] extractAngleArray(Datamodel model, InputStream in) {
        try {
            Angle[] arr = new Angle[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.QANGLE, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Extracts an array of String from the reader
     */
    public static String[] extractStringArray(Datamodel model, InputStream in) {
        try {
            String[] arr = new String[readLittleEndianInt(in)];
            for(int i = 0;i<arr.length;i++) {
                arr[i] = readNullTerminated(in);
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> void readAttributeArray(Datamodel model, InputStream in, EnumAttributeTypes attrType, T[] out) {
        for(int i = 0;i<out.length;i++) {
            out[i] = (T) attrType.extract(model, in).getValue();
        }
    }

}
