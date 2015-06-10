package trail.filesystem;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class FinderTest {

    private FileSystem createNewFileSystem() {
        return Jimfs.newFileSystem(Configuration.unix());
    }
}
