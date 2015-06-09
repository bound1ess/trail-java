package trail.filesystem;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
//import com.google.common.collect.ImmutableList;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.ArrayList;

public class ReaderTest {

    @Test
    public void failsToReadFile() {
        assertEquals(null, Reader.listOfLines("foo" + System.currentTimeMillis()));
    }

    @Test
    public void readsFile() {
        FileSystem fs = createNewFileSystem();
        Path filePath = fs.getPath("/foo");

        List<String> lines = new ArrayList<>();

        lines.add("foo");
        lines.add("bar");
        lines.add("baz");

        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (Exception error) {
            assertTrue(false);
            return;
        }

        // assert
        assertThat(Reader.listOfLines(filePath.toString()), instanceOf(List.class));

        List<String> returned = Reader.listOfLines(filePath.toString());

        assertEquals(returned.size(), 3);
        assertEquals(returned.get(0), "foo");
        assertEquals(returned.get(1), "bar");
        assertEquals(returned.get(2), "baz");
    }

    private FileSystem createNewFileSystem() {
        return Jimfs.newFileSystem(Configuration.unix());
    }
}
