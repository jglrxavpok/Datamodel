package org.jglr.dmx;

import java.util.Arrays;

public final class DMX {

    public static boolean debug = false;

    public static void debug(String message) {
        if(debug) {
            System.out.println("[DMX Debug] "+message);
        }
    }

    public static void info(String message) {
        System.out.println("[DMX Info] "+message);
    }

    public static String toString(Object value) {
        if(value instanceof int[]) {
            return Arrays.toString((int[]) value);
        }
        if(value instanceof float[]) {
            return Arrays.toString((float[]) value);
        }
        if(value instanceof short[]) {
            return Arrays.toString((short[]) value);
        }
        if(value instanceof boolean[]) {
            return Arrays.toString((boolean[]) value);
        }
        if(value instanceof char[]) {
            return Arrays.toString((char[]) value);
        }
        if(value instanceof Object[]) {
            return Arrays.toString((Object[]) value);
        }
        return value.toString();
    }
}
