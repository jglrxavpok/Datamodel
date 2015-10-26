package org.jglr.dmx.codecs;

import org.jglr.dmx.Datamodel;
import org.jglr.dmx.MalformedMDXFile;
import org.jglr.dmx.formats.DMXFormat;
import org.jglr.dmx.formats.FormatModel1;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 class BinaryDMX_v5
 {
     char* header = "<!-- dmx encoding binary 5 format %s %i -->\n";

     int		nStrings; // number of strings in StringDict
     char*	StringDict[]; // null-terminated, tightly packed

     int	nElements; // number of elements in the entire data model

     DmeHeader	ElementIndex; // the root element
     DmeBody		ElementBodies[]; // in the same order as the nested index
 };

 class DmeHeader
 {
     int		Type; // string dictionary index
     int		Name; // string dictionary index
     char	GUID[16]; // little-endian

     DmeHeader SubElems[]; // skip elements which already have an index entry
 };

 class DmeBody
 {
     int	nAttributes;
     DmeAttribute Attributes[];
 };

 class DmAttribute
 {
     int		Name; // string dictionary index
     char	AttributeType; // see below
     void*	Value; // see below
 };
 */
public class Binary5Codec extends DMXCodec {
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
    public Datamodel decode(int encodingVersion, DMXFormat format, InputStream input) throws IOException, MalformedMDXFile {
        DataInputStream in = new DataInputStream(new BufferedInputStream(input));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // read header
        byte b;
        while((b = in.readByte()) != 0) {
            out.write(b);
        }
        out.flush();
        String header = new String(out.toByteArray());
        out.reset();

        String headerRegex = Pattern.quote("<!-- dmx encoding binary 5 format ")+"(.*?) ([0-9]*?)"+Pattern.quote(" -->\n");
        Pattern pattern = Pattern.compile(headerRegex);
        Matcher matcher = pattern.matcher(header);
        if(matcher.find()) {
            if(matcher.groupCount() == 2) {
                System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));
            } else {
                throw new MalformedMDXFile("Missing format informations, invalid header: "+header);
            }
        } else {
            throw new MalformedMDXFile("Missing format informations, invalid header: "+header);
        }
        return null;
    }
}
