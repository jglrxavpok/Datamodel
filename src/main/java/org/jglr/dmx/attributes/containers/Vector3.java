package org.jglr.dmx.attributes.containers;

public class Vector3 {

    private final float x;
    private final float y;
    private final float z;

    public Vector3(float[] values) {
        this(values[0], values[1], values[2]);
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        return "("+x+", "+y+", "+z+")";
    }
}
