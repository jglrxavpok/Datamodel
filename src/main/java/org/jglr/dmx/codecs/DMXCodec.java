package org.jglr.dmx.codecs;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.MalformedMDXException;
import org.jglr.dmx.formats.DMXFormat;

import java.io.IOException;
import java.io.InputStream;

public abstract class DMXCodec {

    public DMXCodec() {
    }

    public abstract boolean supportsBinary();

    public abstract boolean supportsAscii();

    public abstract int[] getSupportedBinaryVersions();

    public abstract int[] getSupportedKeyValues2Versions();

    public abstract boolean supportsFormat(DMXFormat format);

    public abstract byte[] encode(int encodingVersion, DMXFormat format, Datamodel model);

    public abstract Datamodel decode(int encodingVersion, DMXFormat format, InputStream input) throws IOException, MalformedMDXException;
}
