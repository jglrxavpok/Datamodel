package org.jglr.dmx;

import org.jglr.dmx.attributes.AttributeList;
import org.jglr.dmx.codecs.DMXCodec;
import org.jglr.dmx.element.ElementList;
import org.jglr.dmx.formats.DMXFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Datamodel {

    private final DMXCodec codec;
    private final DMXFormat format;
    private final List<String> dictionary;
    private ElementList elementList;
    private AttributeList prefixList;

    /**
     * Used for creating datamodel from code
     * @param format
     *              The format to write the datamodel to, user-dependent
     */
    public Datamodel(DMXFormat format) {
        this(null, format == null ? new DMXFormat() : format, new String[0]);
    }

    /**
     * Constructor used when creating a Datamodel from an {@link java.io.InputStream}
     * @param codec
     *             The codec used to read the datamodel
     * @param format
     *              The format is which the datamodel was encoded in
     * @param dictionary
     *                  The String dictionary of the datamodel
     */
    public Datamodel(DMXCodec codec, DMXFormat format, String[] dictionary) {
        this.codec = codec;
        this.format = format;
        this.dictionary = new ArrayList<>();
        Collections.addAll(this.dictionary, dictionary);
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

    public List<String> getDictionary() {
        return dictionary;
    }

    public DMXFormat getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return "Datamodel(codec="+codec+", format="+format.toString()+", dictionarySize="+dictionary.size()+")";
    }
}
