package org.jglr.dmx.codecs;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.UnsupportedDMXException;
import org.jglr.dmx.MalformedDMXException;
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

    public abstract Datamodel decode(int encodingVersion, InputStream input) throws IOException, MalformedDMXException, UnsupportedDMXException;

    public boolean supportsKeyValues2Version(int version) {
        if(!supportsBinary())
            return false;
        int[] supportedVersions = getSupportedKeyValues2Versions();
        for(int v : supportedVersions) {
            if(v == version)
                return true;
        }
        return false;
    }

    public boolean supportsBinaryVersion(int version) {
        if(!supportsBinary())
            return false;
        int[] supportedVersions = getSupportedBinaryVersions();
        for(int v : supportedVersions) {
            if(v == version)
                return true;
        }
        return false;
    }
}
