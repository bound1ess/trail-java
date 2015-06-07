package trail.input;

import org.junit.Test;
import static org.junit.Assert.*;

public class InputArgumentTest {

    @Test
    public void returnsArgumentName() {
        assertEquals(new InputArgument("foo", true).getName(), "foo");
    }

    @Test
    public void returnsTrueIfRequired() {
        assertEquals(new InputArgument("foo", true).isRequired(), true);
    }

    @Test
    public void returnsFalseIfNotRequired() {
        assertEquals(new InputArgument("foo", false).isRequired(), false);
    }
}
