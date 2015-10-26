package org.jglr.dmx.codecs;

import org.jglr.dmx.formats.DMXFormat;
import org.jglr.dmx.formats.FormatModel1;

import java.util.HashMap;

public final class DMXCodecRegistry {

    public static final DMXCodecRegistry singleton = new DMXCodecRegistry();

    private final HashMap<DMXFormat, DMXCodec> registry;

    private DMXCodecRegistry() {
        registry = new HashMap<>();
        registerCodec(new FormatModel1(), new Binary5Codec());
    }

    public void registerCodec(DMXFormat format, DMXCodec codec) {
        registry.put(format, codec);
    }

    public void registerCodec(String name, int version, DMXCodec codec) {
        registerCodec(new DMXFormat() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public int getVersion() {
                return version;
            }
        }, codec);
    }

    public DMXCodec getCodec(DMXFormat format) {
        return registry.get(format);
    }

    public DMXCodec getCodec(String name, int version) {
        return getCodec(new DMXFormat() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public int getVersion() {
                return version;
            }
        });
    }
}
