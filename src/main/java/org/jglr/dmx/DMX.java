package org.jglr.dmx;

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
}
