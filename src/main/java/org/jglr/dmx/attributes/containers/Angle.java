package org.jglr.dmx.attributes.containers;

/**
 * Represents a pitch/yaw/roll angle.<br/>
 * Each component is respectively represented by the x, y and z components of this object.
 */
public class Angle {

    private final float x;
    private final float y;
    private final float z;

    public Angle(float[] values) {
        this(values[0], values[1], values[2]);
    }

    public Angle(float x, float y, float z) {
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
