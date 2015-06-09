package trail.filesystem;

import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Finder {
    private final Set<String> extensions;
    private final ExtensionFilter extensionFilter;

    public Finder(final List<String> extensions) {
        this.extensions = new HashSet<>();

        for (String extension: extensions) {
            this.extensions.add(extension);
        }

        this.extensionFilter = new ExtensionFilter(this.extensions);
    }

    public List<String> recursiveTraversal(final String src) {
        List<String> files = new ArrayList<>();

        try {
            File rootNode = new File(src);

            for (File file: rootNode.listFiles()) {
                if (file.isDirectory()) {
                    for (String fileName: recursiveTraversal(file.getPath())) {
                        files.add(fileName);
                    }
                } else if (this.extensionFilter.isAccepted(file.getName())) {
                    files.add(file.getPath());
                }
            }
        } catch (Exception error) {
            // don't handle, throw again
            throw error;
        }

        return files;
    }

    class ExtensionFilter {
        private final Set<String> extensions;

        public ExtensionFilter(final Set<String> extensions) {
            this.extensions = extensions;
        }

        public boolean isAccepted(final String name) {
            int dotIndex = name.lastIndexOf('.');

            // Files without any extension are ignored.
            if (dotIndex == -1) {
                return false;
            }

            return this.extensions.contains(name.substring(dotIndex + 1));
        }
    }
}
