package org.jglr.dmx.attributes.containers;

import java.util.Arrays;

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
