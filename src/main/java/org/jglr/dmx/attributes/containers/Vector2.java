package org.jglr.dmx.attributes.containers;

public class Vector2 {

    private final float x;
    private final float y;

    public Vector2(float[] values) {
        this(values[0], values[1]);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
}
