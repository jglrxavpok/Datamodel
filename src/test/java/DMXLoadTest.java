import org.jglr.dmx.DMX;
import org.jglr.dmx.DMXException;
import org.jglr.dmx.Datamodel;
import org.jglr.dmx.codecs.BinaryModelCodec;
import org.jglr.dmx.codecs.DMXCodec;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class DMXLoadTest {

    @Before
    public void init() {
        DMX.debug = true;
    }

    @Test
    public void testLoad() throws IOException, DMXException {
        InputStream model = getClass().getResourceAsStream("/Gun.dmx");
        DMXCodec codec = new BinaryModelCodec();
        Datamodel datamodel = codec.decode(codec.getSupportedBinaryVersions()[0], model);
    }

}
