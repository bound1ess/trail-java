package trail.filesystem;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import com.google.common.jimfs.Configuration;
import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Jimfs;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

public class FinderTest {
    private final List<String> extensions;

    public FinderTest() {
        this.extensions = new ArrayList<>();
        this.extensions.add("txt");
    }

    @Test
    public void failsToScanDirectory() {
        Path path = Paths.get("foo" + System.currentTimeMillis());
        assertEquals(null, new Finder(this.extensions).recursiveTraversal(path));
    }

    @Test
    public void scansDirectory() {
        FileSystem fs = createNewFileSystem();
        Path dir = fs.getPath("/foo");

        try {
            Files.createDirectory(dir);
            Files.write(
                dir.resolve("bar.txt"), ImmutableList.of("123"), StandardCharsets.UTF_8
            );

            Files.createDirectory(dir.resolve("baz"));
            Files.write(
                dir.resolve("baz").resolve("fizz.txt"),
                ImmutableList.of("Hello!"),
                StandardCharsets.UTF_8
            );
        } catch (Exception error) {
            assertTrue(false);
            return;
        }

        Finder finder = new Finder(this.extensions);

        assertThat(finder.recursiveTraversal(dir), instanceOf(List.class));

        List<String> files = finder.recursiveTraversal(dir);

        assertEquals(files.size(), 2);
        assertTrue(files.indexOf("bar.txt") != -1);
        assertTrue(files.indexOf("fizz.txt") != -1);
    }

    private FileSystem createNewFileSystem() {
        return Jimfs.newFileSystem(Configuration.unix());
    }
}
