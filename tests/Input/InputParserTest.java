package trail.input;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Map;

public class InputParserTest {

    @Test
    public void parsesArgumentList() {
        String[] arguments = {
            "--dir", "\"foo\"", "--fix",
        };

        Map<String, String> config = InputParser.parse(arguments);

        assertEquals(config.get("dir"), "foo");
        assertEquals(config.get("fix"), "yes");
        assertEquals(config.get("verbose"), "no");
        assertEquals(config.get("debug"), "no");
    }

    @Test
    public void alsoParsesArgumentList() {
        String[] arguments = {
            "--dir='foo'", "--fix", "--debug", "--verbose",
        };

        Map<String, String> config = InputParser.parse(arguments);

        assertEquals(config.get("dir"), "foo");
        assertEquals(config.get("fix"), "yes");
        assertEquals(config.get("verbose"), "yes");
        assertEquals(config.get("debug"), "yes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnInvalidInput() {
        String[] arguments = {
            "--dir",
        };

        InputParser.parse(arguments);
    }

    @Test(expected = IllegalArgumentException.class)
    public void alsoFailsOnInvalidInput() {
        String[] arguments = {
            "--fix", "--debug", "--verbos3", // <= spelling mistake
        };

        InputParser.parse(arguments);
    }
}
