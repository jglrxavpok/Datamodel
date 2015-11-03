package org.jglr.dmx.codecs;

import org.jglr.dmx.*;
import org.jglr.dmx.attributes.Attribute;
import org.jglr.dmx.attributes.AttributeValue;
import org.jglr.dmx.attributes.EnumAttributeTypes;
import org.jglr.dmx.element.Element;
import org.jglr.dmx.formats.DMXFormat;
import org.jglr.dmx.formats.FormatModel1;

import java.io.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jglr.dmx.utils.IOUtils.*;

/**
 * Codec only able to read binary DMX files with the 'model' format
 * Structure of a binary DMX file, according to https://developer.valvesoftware.com/wiki/DMX/Binary
 <pre>{@code class BinaryDMX_v5
 {
     char* header = "<!-- dmx encoding binary 5 format %s %i -->\n";

     int nStrings; // number of strings in StringDict
     char* StringDict[]; // null-terminated, tightly packed

     int nElements; // number of elements in the entire data model

     DmeHeader ElementIndex; // the root element
     DmeBody ElementBodies[]; // in the same order as the nested index
 };

 class DmeHeader
 {
     int Type; // string dictionary index
     int Name; // string dictionary index
     char GUID[16]; // little-endian

     DmeHeader SubElems[]; // skip elements which already have an index entry
 };

 class DmeBody
 {
     int nAttributes;
     DmeAttribute Attributes[];
 };

 class DmAttribute
 {
     int Name; // string dictionary index
     char AttributeType; // see below
     void* Value; // see below
 };
 }</pre>
 */
public class BinaryModelCodec extends DMXCodec {
    @Override
    public boolean supportsBinary() {
        return true;
    }

    @Override
    public boolean supportsAscii() {
        return false;
    }

    @Override
    public int[] getSupportedBinaryVersions() {
        return new int[]{5};
    }

    @Override
    public int[] getSupportedKeyValues2Versions() {
        return new int[0];
    }

    @Override
    public boolean supportsFormat(DMXFormat format) {
        return FormatModel1.instance.equals(format);
    }

    @Override
    public byte[] encode(int encodingVersion, DMXFormat format, Datamodel model) {
        return new byte[0];
    }

    @Override
    public Datamodel decode(int encodingVersion, InputStream input) throws IOException, MalformedDMXException, UnsupportedDMXException {
        BufferedInputStream in = new BufferedInputStream(input);

        // Read header
        String header = readNullTerminated(in);

        String headerRegex = Pattern.quote("<!-- dmx encoding binary ")+"([0-9]*?)"+Pattern.quote(" format ")+"(.*?) ([0-9]*?)"+Pattern.quote(" -->");
        Pattern pattern = Pattern.compile(headerRegex);
        Matcher matcher = pattern.matcher(header);
        if(matcher.find()) {
            if(matcher.groupCount() == 3) {
                int binaryVersion = Integer.parseInt(matcher.group(1));
                if(!supportsBinaryVersion(binaryVersion)) {
                    throw new UnsupportedDMXException("Binary version "+binaryVersion+" is not supported");
                }
                String format = matcher.group(2);
                int formatVersion = Integer.parseInt(matcher.group(3));
                if(!supportsFormat(new DMXFormat(format, formatVersion)))
                    throw new UnsupportedDMXException("Format "+format+" of version "+formatVersion+" is not supported");
            } else {
                throw new MalformedDMXException("Missing format informations, invalid header: "+header);
            }
        } else {
            throw new MalformedDMXException("Missing format informations, invalid header: "+header);
        }

        // Format and versions are fine, we can continue

        int dictionarySize = readLittleEndianInt(in);
        DMX.debug("Dictionary size: "+dictionarySize);
        String[] dictionary = new String[dictionarySize];

        // Fills string dictionnary
        for(int i = 0;i<dictionarySize;i++) {
            dictionary[i] = readNullTerminated(in);
            DMX.debug("Added \"" + dictionary[i] + "\" into StrDictionary at index " + i);
        }

        Datamodel datamodel = new Datamodel(this, dictionary);

        int elements = readLittleEndianInt(in);
        DMX.debug("Found "+elements+" elements");

        // Read elements
        for(int i = 0;i<elements;i++) {
            Element element = readElement(datamodel, in, dictionary);
            datamodel.getElementList().add(element);
        }

        // Read element bodies, aka. their attributes
        for(Element elem : datamodel.getElementList()) {
            int attributeCount = readLittleEndianInt(in);
            DMX.debug(elem.toString()+" has "+attributeCount+" attribute(s).");

            for(int i = 0;i<attributeCount;i++) {
                // Read a single attribute
                String name = dictionary[readLittleEndianInt(in)];
                EnumAttributeTypes type = EnumAttributeTypes.getType(readByte(in));
                AttributeValue value = type.extract(datamodel, in);
                Attribute attribute = new Attribute(name, value, elem);
                elem.add(attribute);
                DMX.debug("Attribute("+i+"): "+name+" = "+value);
            }
        }

        return datamodel;
    }

    /**
     * Reads a single element from the reader.
     * @param datamodel
     *                  The datamodel currently being loaded
     * @param in
     *          The reader from which to read the element
     * @param dictionary
     *                 The String dictionary
     * @return
     *      The read Element
     * @throws IOException
     */
    private Element readElement(Datamodel datamodel, InputStream in, String[] dictionary) throws IOException {
        int className = readLittleEndianInt(in);
        int name = readLittleEndianInt(in);
        byte[] uuid = readLittleEndianUUID(in);
        Element element = new Element(datamodel, dictionary[name], dictionary[className], UUID.nameUUIDFromBytes(uuid));
        DMX.debug(element.toString());
        return element;
    }

}
