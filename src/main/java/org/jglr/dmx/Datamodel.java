package org.jglr.dmx;

import org.jglr.dmx.attributes.Attribute;
import org.jglr.dmx.attributes.AttributeList;
import org.jglr.dmx.attributes.EnumAttributeTypes;
import org.jglr.dmx.codecs.DMXCodec;
import org.jglr.dmx.element.Element;
import org.jglr.dmx.element.ElementList;
import org.jglr.dmx.formats.DMXFormat;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    public Element createElement() {
        return createElement("StubElement");
    }

    public Element createElement(String name) {
        return createElement(name, (UUID)null);
    }

    public Element createElement(String name, @Nullable UUID uuid) {
        return createElement(name, "DmElement", uuid);
    }

    public Element createElement(String name, String className) {
        return createElement(name, className, null);
    }

    public Element createElement(String name, String className, @Nullable UUID uuid) {
        return createElement(name, className, uuid, false);
    }

    public Element createElement(String name, String className, @Nullable UUID uuid, boolean stub) {
        Element elem = new Element(this, name, className, uuid, stub);
        addElement(elem);
        return elem;
    }

    public Datamodel addElement(Element elem) {
        if(elem.getOwner() != this) {
            throw new IllegalArgumentException("Element does not have the same owner as the datamodel to which it was added");
        }
        getElementList().add(elem);
        return this;
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

    public void refreshDictionary() {
        dictionary.clear();
        addAttrListToDict(getPrefixList());

        // Add attribute names to the dictionary
        getElementList().stream()
                .forEach(this::addAttrListToDict);

        // Add element names to the dictionary
        getElementList().stream()
                .map(Element::getName)
                .forEach(this::addToDictIfNotPresent);

        // Add element class names to the dictionary
        getElementList().stream()
                .map(Element::getClassName)
                .forEach(this::addToDictIfNotPresent);
    }

    private void addAttrListToDict(AttributeList list) {
        list.stream()
                .map(Attribute::getName)
                .forEach(this::addToDictIfNotPresent);

        list.forEach(attr -> {
            if(attr.getValue().getType() == EnumAttributeTypes.STRING) {
                addToDictIfNotPresent((String) attr.getValue().getValue());
            }
        });
    }

    private void addToDictIfNotPresent(String val) {
        if(!dictionary.contains(val))
            dictionary.add(val);
    }

    @Override
    public String toString() {
        return "Datamodel(codec="+codec+", format="+format.toString()+", dictionarySize="+dictionary.size()+")";
    }

    public int getStrIndex(String str) {
        return dictionary.indexOf(str);
    }
}
