import org.jglr.dmx.Datamodel;
import org.jglr.dmx.MalformedMDXException;
import org.jglr.dmx.codecs.Binary5Codec;
import org.jglr.dmx.codecs.DMXCodec;
import org.jglr.dmx.formats.FormatModel1;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class DMXLoadTest {

    @Test
    public void testLoad() throws IOException, MalformedMDXException {
        InputStream model = getClass().getResourceAsStream("/Gun.dmx");
        DMXCodec codec = new Binary5Codec();
        Datamodel datamodel = codec.decode(codec.getSupportedBinaryVersions()[0], FormatModel1.instance, model);
    }

}
