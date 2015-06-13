package trail.output;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;

import java.io.BufferedWriter;

import java.util.List;

public class OutputWriter {

    public static boolean writeList(final Path to, final List<String> lines) {
        BufferedWriter writer = null;

        try {
            writer = Files.newBufferedWriter(to, StandardCharsets.UTF_8);
        } catch (Exception error) {
            return false;
        }

        for (String line: lines) {
            try {
                writer.write(line);
                writer.newLine();
            } catch (Exception error) {
                return false;
            }
        }

        try {
            writer.close();
        } catch (Exception error) {
            return false;
        } finally {
            return true;
        }
    }
}
