package trail.filesystem;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
//import com.google.common.collect.ImmutableList;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;
import org.junit.Test;

public class ReaderTest {

    @Test
    public void failsToReadFile() {
        assertEquals(null, Reader.listOfLines("foo" + System.currentTimeMillis()));
    }

    private FileSystem createNewFileSystem() {
        return Jimfs.newFileSystem(Configuration.unix());
    }
}
