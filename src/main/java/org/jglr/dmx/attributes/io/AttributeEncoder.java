package org.jglr.dmx.attributes.io;

import org.jglr.dmx.Datamodel;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface AttributeEncoder {

    /**
     * Encodes a single value to a OutputStream.<br/>
     * Format is dependent on the encoder type
     * @param model
     * @param out
     * @param value
     */
    void encode(Datamodel model, OutputStream out, Object value) throws IOException;

}
