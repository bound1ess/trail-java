package trail;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.ArrayList;

public class ValidatorTest {

    @Test
    public void checksIfBroken() {
        List<String> lines = new ArrayList<>();

        lines.add("foo\n");
        lines.add("\n");
        lines.add("bar\nbaz\n");

        assertEquals(null, Validator.findBrokenLines(lines));

        lines.clear();
        lines.add("foo \n");

        List<Integer> brokenLines = Validator.findBrokenLines(lines);

        assertThat(brokenLines, instanceOf(List.class));
        assertEquals((int) brokenLines.size(), 1);
        assertEquals(brokenLines.get(0), Integer.valueOf(1));

        lines.clear();
        lines.add("foo");
        lines.add("bar\n");
        lines.add("baz    ");

        brokenLines = Validator.findBrokenLines(lines);

        assertThat(brokenLines, instanceOf(List.class));
        assertEquals((int) brokenLines.size(), 1);
        assertEquals(brokenLines.get(0), Integer.valueOf(3));
    }

    @Test
    public void removesTrailingWhitespaces() {
        List<String> lines = new ArrayList<>();

        lines.add("foo\n");
        lines.add(" bar \n");
        lines.add("baz   ");

        lines = Validator.fixBrokenLines(lines);

        assertEquals(lines.size(), 3);
        assertEquals(lines.get(0), "foo\n");
        assertEquals(lines.get(1), " bar\n");
        assertEquals(lines.get(2), "baz\n");
    }
}
