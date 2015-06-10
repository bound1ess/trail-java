package trail.filesystem;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;

public class Finder {
    private final Set<String> extensions;

    public Finder(final List<String> extensions) {
        this.extensions = new HashSet<>();

        for (String extension: extensions) {
            this.extensions.add(extension);
        }
    }

    public List<String> recursiveTraversal(final Path src) {
        if ( ! Files.isReadable(src) || ! Files.isDirectory(src)) {
            return null;
        }

        List<String> files = new ArrayList<>();

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(src);

            for (Path path: stream) {
                if (Files.isDirectory(path)) {
                    for (String file: recursiveTraversal(path)) {
                        files.add(file);
                    }
                } else {
                    files.add(path.toString());
                }
            }
        } catch (Exception error) {
            return null;
        }

        return files;
    }

    private boolean isAccepted(final String name) {
        int dotIndex = name.lastIndexOf('.');

        // Files without any extension are ignored.
        if (dotIndex == -1) {
            return false;
        }

        return this.extensions.contains(name.substring(dotIndex + 1));
    }
}
