import org.jglr.dmx.DMX;
import org.jglr.dmx.Datamodel;
import org.jglr.dmx.formats.DMXFormat;
import org.junit.Before;
import org.junit.Test;

public class DMXSaveTest {

    @Before
    public void init() {
        DMX.debug = true;
    }

    @Test
    public void testSave() {
        Datamodel model = new Datamodel(new DMXFormat("model", 1));
    }
}
