package org.jglr.dmx.attributes.containers;

/**
 * Represents a color composed of red, blue and green light.
 * There is an extra component for opacity (alpha).
 * Each component ranges from 0 to 255 (included).
 */
public class Color {

    private final int red;
    private final int green;
    private final int blue;
    private final int alpha;

    public Color(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getRed() {
        return red;
    }

    public int toRGBAInt() {
        return red << 24 | green << 16 | blue << 8 | alpha;
    }

    public int toARGBInt() {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    @Override
    public String toString() {
        return "col("+red+", "+green+", "+blue+", "+alpha+")";
    }
}
