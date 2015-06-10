package trail;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

public class ValidatorTest {

    @Test
    public void checksIfBroken() {
        List<String> lines = new ArrayList<>();

        lines.add("foo\n");
        lines.add("\n");
        lines.add("bar\nbaz\n");

        assertFalse(Validator.isBroken(lines));

        lines.clear();
        lines.add("foo \n");

        assertTrue(Validator.isBroken(lines));

        lines.clear();
        lines.add("foo");
        lines.add("bar\n");
        lines.add("baz    ");

        assertTrue(Validator.isBroken(lines));
    }

    @Test
    public void removesTrailingWhitespaces() {
        List<String> lines = new ArrayList<>();

        lines.add("foo\n");
        lines.add(" bar \n");
        lines.add("baz   ");

        lines = Validator.fix(lines);

        assertEquals(lines.size(), 3);
        assertEquals(lines.get(0), "foo\n");
        assertEquals(lines.get(1), " bar\n");
        assertEquals(lines.get(2), "baz\n");
    }
}