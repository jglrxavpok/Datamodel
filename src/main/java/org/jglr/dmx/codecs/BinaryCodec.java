package org.jglr.dmx.codecs;

import org.jglr.dmx.*;
import org.jglr.dmx.attributes.Attribute;
import org.jglr.dmx.attributes.AttributeValue;
import org.jglr.dmx.attributes.EnumAttributeTypes;
import org.jglr.dmx.element.Element;
import org.jglr.dmx.formats.DMXFormat;

import java.io.*;
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
public class BinaryCodec extends DMXCodec {
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
    public byte[] encode(int encodingVersion, Datamodel model) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(baos);

        // Write the header
        // TODO: Move this somewhere else
        String generatedHeader = String.format("<!-- dmx encoding binary %d format %s %d -->\n", encodingVersion, model.getFormat().getName(), model.getFormat().getVersion());
        out.write(generatedHeader.getBytes());
        out.write(0); // Write the null character

        model.refreshDictionary();

        // Write String Dictionary
        int dictSize = model.getDictionary().size();

        writeLittleEndianInt(out, dictSize);
        for(int i = 0;i<dictSize;i++) {
            writeNullTerminatedString(out, model.getDictionary().get(i));
        }

        // Write elements
        int elemCount = model.getElementList().size();
        writeLittleEndianInt(out, elemCount);

        // Write element headers
        for(Element elem : model.getElementList()) {
            int nameIndex = model.getStrIndex(elem.getName());
            int classNameIndex = model.getStrIndex(elem.getClassName());
            writeLittleEndianInt(out, nameIndex);
            writeLittleEndianInt(out, classNameIndex);
            writeLittleEndianUUID(out, elem.getUuid());
        }

        // Write element bodies
        for(Element elem : model.getElementList()) {
            writeLittleEndianInt(out, elem.size());
            for(Attribute attribute : elem) {
                int nameIndex = model.getStrIndex(attribute.getName());
                EnumAttributeTypes type = attribute.getValue().getType();
                writeLittleEndianInt(out, nameIndex);
                writeByte(out, (byte) type.value());
                System.out.println(type+" "+attribute.getName());
                type.write(model, out, attribute.getValue().getRawValue());
            }
        }

        out.flush();
        out.close();
        return baos.toByteArray();
    }

    @Override
    public Datamodel decode(int encodingVersion, InputStream input) throws IOException, MalformedDMXException, UnsupportedDMXException {
        BufferedInputStream in = new BufferedInputStream(input);

        // Read header
        DMXFormat format;
        String header = readNullTerminated(in);

        DMX.debug("Read header: "+header);

        // TODO: Get rid of the encoding check, that needs to be done somewhere else
        String headerRegex = Pattern.quote("<!-- dmx encoding binary ")+"([0-9]*?)"+Pattern.quote(" format ")+"(.*?) ([0-9]*?)"+Pattern.quote(" -->");
        Pattern pattern = Pattern.compile(headerRegex);
        Matcher matcher = pattern.matcher(header);
        if(matcher.find()) {
            if(matcher.groupCount() == 3) {
                int binaryVersion = Integer.parseInt(matcher.group(1));
                if(!supportsBinaryVersion(binaryVersion)) {
                    throw new UnsupportedDMXException("Binary version "+binaryVersion+" is not supported");
                }
                String formatName = matcher.group(2);
                int formatVersion = Integer.parseInt(matcher.group(3));
                format = new DMXFormat(formatName, formatVersion);
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

        Datamodel datamodel = new Datamodel(this, format, dictionary);

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
                EnumAttributeTypes type = EnumAttributeTypes.getType(readUnsignedByte(in));
                DMX.debug("Attribute("+i+"): "+name+" type: "+type.name());
                AttributeValue value = type.extract(datamodel, in);
                Attribute attribute = new Attribute(name, value, elem);
                elem.add(attribute);
                String strValue = DMX.toString(value.getRawValue());
                DMX.debug("Attribute("+i+"): "+name+" = "+strValue);
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
        Element element = new Element(datamodel, dictionary[name], dictionary[className], getUUIDFromBytes(uuid));
        DMX.debug(element.toString());
        return element;
    }

}
