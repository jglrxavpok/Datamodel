package org.jglr.dmx.attributes.containers;

import java.util.Arrays;

/**
 * Represents a 4x4 matrix.
 * The order is format-dependant.
 * <br/>
 * <b>Warning: </b>Matrix4 is only a container, you should back it with your equivalent Matrix objects
 */
public class Matrix4 {

    private final float[] raw;

    public Matrix4(float[] values) {
        this.raw = values;
    }

    public float[] getRaw() {
        return raw;
    }

    @Override
    public String toString() {
        return "mat4x4("+Arrays.toString(raw)+")";
    }
}
