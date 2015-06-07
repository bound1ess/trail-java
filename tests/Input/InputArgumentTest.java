package trail.input;

import org.junit.Test;
import static org.junit.Assert.*;

public class InputArgumentTest {

    @Test
    public void returnsArgumentName() {
        assertEquals(new InputArgument("foo", true).getName(), "foo");
    }
}
