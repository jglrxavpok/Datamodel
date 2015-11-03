package org.jglr.dmx;

import org.jglr.dmx.attributes.AttributeList;
import org.jglr.dmx.codecs.DMXCodec;
import org.jglr.dmx.element.ElementList;
import org.jglr.dmx.formats.DMXFormat;

public class Datamodel {

    private final DMXCodec codec;
    private final DMXFormat format;
    private final String[] dictionary;
    private ElementList elementList;
    private AttributeList prefixList;

    public Datamodel(DMXCodec codec, DMXFormat format, String[] dictionary) {
        this.codec = codec;
        this.format = format;
        this.dictionary = dictionary;
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

    public String[] getDictionary() {
        return dictionary;
    }

    public DMXFormat getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return "Datamodel(codec="+codec+", format="+format.toString()+", dictionarySize="+dictionary.length+")";
    }
}
