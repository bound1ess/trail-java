package trail.output;

import org.junit.Test;
import static org.junit.Assert.*;

//import static org.hamcrest.CoreMatchers.*;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import java.io.BufferedReader;

import java.util.List;
import java.util.ArrayList;

public class OutputWriterTest {

    @Test
    public void writesToFile() {
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        Path filePath = fs.getPath("/foo");
        List<String> lines = new ArrayList<>();

        lines.add("foo");
        lines.add("bar");
        lines.add("baz");

        assertTrue(OutputWriter.writeList(filePath, lines));

        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
        } catch (Exception error) {
            assertTrue(false);
            return;
        }

        try {
            assertEquals(reader.readLine(), "foo");
            assertEquals(reader.readLine(), "bar");
            assertEquals(reader.readLine(), "baz");
        } catch (Exception error) {
            assertTrue(false);
            return;
        }

        try {
            reader.close();
        } catch (Exception error) {}
    }
}
