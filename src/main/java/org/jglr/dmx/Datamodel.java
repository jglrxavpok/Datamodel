package org.jglr.dmx;

import org.jglr.dmx.codecs.DMXCodec;

public class Datamodel {

    private final DMXCodec codec;
    private ElementList elementList;
    private AttributeList prefixList;

    public Datamodel(DMXCodec codec) {
        this.codec = codec;
    }

    public DMXCodec getCodec() {
        return codec;
    }

    public ElementList getElementList() {
        return elementList;
    }

    public AttributeList getPrefixList() {
        return prefixList;
    }
}
