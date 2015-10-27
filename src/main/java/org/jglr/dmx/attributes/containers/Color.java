package org.jglr.dmx.attributes.containers;

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

    @Override
    public String toString() {
        return "col("+red+", "+green+", "+blue+", "+alpha+")";
    }
}
