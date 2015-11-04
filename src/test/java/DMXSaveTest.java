import org.jglr.dmx.DMX;
import org.jglr.dmx.Datamodel;
import org.jglr.dmx.MalformedDMXException;
import org.jglr.dmx.UnsupportedDMXException;
import org.jglr.dmx.codecs.BinaryCodec;
import org.jglr.dmx.codecs.DMXCodec;
import org.jglr.dmx.element.Element;
import org.jglr.dmx.formats.DMXFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class DMXSaveTest {

    @Before
    public void init() {
        DMX.debug = true;
    }

    @Test
    public void testSaveAndRead() throws UnsupportedDMXException, IOException, MalformedDMXException {
        Datamodel model = new Datamodel(new DMXFormat("model", 1));
        Element root = model.createElement("Root", "RootElement");
        root.createAttribute("MyFloat!", 0f);

        DMXCodec codec = new BinaryCodec();
        byte[] data = codec.encode(5, model);
        Datamodel read = codec.decode(5, new ByteArrayInputStream(data));
        assertTrue("Read format and model format are not equal", read.getFormat().equals(model.getFormat()));
    }
}
