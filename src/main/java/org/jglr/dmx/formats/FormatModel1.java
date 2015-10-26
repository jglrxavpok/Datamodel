package org.jglr.dmx.formats;

public class FormatModel1 extends DMXFormat {

    public static final FormatModel1 instance = new FormatModel1();

    @Override
    public String getName() {
        return "model";
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
