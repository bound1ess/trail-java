package trail.filesystem;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class Reader {

    public static List<String> listOfLines(final Path src) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(src, StandardCharsets.UTF_8);
        } catch (Exception error) {
            return null;
        }

        return lines;
    }
}
