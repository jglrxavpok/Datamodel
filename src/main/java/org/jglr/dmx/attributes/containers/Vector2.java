package org.jglr.dmx.attributes.containers;

/**
 * Represents a vector composed of 2 components: x, y.
 * <br/>
 * <b>Warning: </b>Vector2 is only a container, you should back it with your equivalent Vector objects
 */
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
