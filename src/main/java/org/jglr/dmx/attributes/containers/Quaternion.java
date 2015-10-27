package org.jglr.dmx.attributes.containers;

/**
 * Represents a quaternion composed of 4 components: x, y, z, w.
 * <br/>
 * <b>Warning: </b>Quaternion is only a container, you should back it with your equivalent Quaternion objects
 */
public class Quaternion {

    private final float x;
    private final float y;
    private final float z;
    private final float w;

    public Quaternion(float[] values) {
        this(values[0], values[1], values[2], values[3]);
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getW() {
        return w;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "("+x+", "+y+", "+z+", "+w+")";
    }
}
