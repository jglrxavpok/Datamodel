package org.jglr.dmx;

import org.jglr.dmx.attributes.AttributeList;
import org.jglr.dmx.codecs.DMXCodec;
import org.jglr.dmx.element.ElementList;

public class Datamodel {

    private final DMXCodec codec;
    private final String[] directory;
    private ElementList elementList;
    private AttributeList prefixList;

    public Datamodel(DMXCodec codec, String[] directory) {
        this.codec = codec;
        this.directory = directory;
        elementList = new ElementList(this);
        prefixList = new AttributeList(this);
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

    public String[] getDirectory() {
        return directory;
    }
}
