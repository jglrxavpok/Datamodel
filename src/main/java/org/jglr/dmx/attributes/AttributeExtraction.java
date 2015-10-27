package org.jglr.dmx.attributes;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.attributes.containers.*;
import org.jglr.dmx.element.Element;
import org.jglr.dmx.element.StubElement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.UUID;

import static org.jglr.dmx.utils.IOUtils.*;

public final class AttributeExtraction {

    public static Element extractElement(Datamodel model, Reader in) {
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

    public static int extractInt(Datamodel model, Reader in) {
        try {
            return readLittleEndianInt(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static float extractFloat(Datamodel model, Reader in) {
        try {
            return readFloat(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0f;
    }

    public static boolean extractBool(Datamodel model, Reader in) {
        try {
            return readBool(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int extractCChar(Datamodel model, Reader in) {
        try {
            return readByte(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String extractString(Datamodel model, Reader in) {
        try {
            int index = readLittleEndianInt(in);
            return model.getDirectory()[index];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Color extractColor(Datamodel model, Reader in) {
        return new Color(extractCChar(model, in), extractCChar(model, in), extractCChar(model, in), extractCChar(model, in));
    }

    public static Vector2 extractVec2(Datamodel model, Reader in) {
        return new Vector2(readFloatVector(in, 2));
    }

    public static Vector3 extractVec3(Datamodel model, Reader in) {
        return new Vector3(readFloatVector(in, 3));
    }

    public static Angle extractAngle(Datamodel model, Reader in) {
        return new Angle(readFloatVector(in, 3));
    }

    public static Vector4 extractVec4(Datamodel model, Reader in) {
        return new Vector4(readFloatVector(in, 4));
    }

    public static Quaternion extractQuat(Datamodel model, Reader in) {
        return new Quaternion(readFloatVector(in, 4));
    }

    public static Matrix4 extractMat(Datamodel model, Reader in) {
        return new Matrix4(readFloatVector(in, 4*4));
    }

    private static float[] readFloatVector(Reader in, int dimensions) {
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
    public static Element[] extractElementArray(Datamodel model, Reader in) {
        try {
            Element[] arr = new Element[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.ELEMENT, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] extractIntArray(Datamodel model, Reader in) {
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

    public static float[] extractFloatArray(Datamodel model, Reader in) {
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

    public static boolean[] extractBoolArray(Datamodel model, Reader in) {
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

    public static Color[] extractColorArray(Datamodel model, Reader in) {
        try {
            Color[] arr = new Color[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.COLOR, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Vector2[] extractVector2Array(Datamodel model, Reader in) {
        try {
            Vector2[] arr = new Vector2[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VECTOR2, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Vector3[] extractVector3Array(Datamodel model, Reader in) {
        try {
            Vector3[] arr = new Vector3[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VECTOR3, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Vector4[] extractVector4Array(Datamodel model, Reader in) {
        try {
            Vector4[] arr = new Vector4[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VECTOR4, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Quaternion[] extractQuaternionArray(Datamodel model, Reader in) {
        try {
            Quaternion[] arr = new Quaternion[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.QUATERNION, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Matrix4[] extractMatrix4Array(Datamodel model, Reader in) {
        try {
            Matrix4[] arr = new Matrix4[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.VMATRIX, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Angle[] extractAngleArray(Datamodel model, Reader in) {
        try {
            Angle[] arr = new Angle[readLittleEndianInt(in)];
            readAttributeArray(model, in, EnumAttributeTypes.QANGLE, arr);
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] extractStringArray(Datamodel model, Reader in) {
        try {
            String[] arr = new String[readLittleEndianInt(in)];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for(int i = 0;i<arr.length;i++) {
                arr[i] = readNullTerminated(in, out);
            }
            out.close();
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> void readAttributeArray(Datamodel model, Reader in, EnumAttributeTypes attrType, T[] out) {
        for(int i = 0;i<out.length;i++) {
            out[i] = (T) attrType.handle(model, in).getValue();
        }
    }

}
