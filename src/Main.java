package trail;

import trail.input.InputParser;
import trail.filesystem.Finder;
import trail.filesystem.Reader;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class Main {
    private final static String VERSION = "development"; // @todo
    private final static String CONFIG_FILE = ".trail-config";

    private final static String[] DEFAULT_EXTENSIONS = {
        "c", "cpp", "h", "hpp", // C and C++ (and header files)
        "java", // Java
        "php", "rb", "py", // interp. languages
        "js", "coffee", // JS and its friend
        "html", "css", // web development
        "bash", "sh", // Bash, Shell
        "md", // Markdown
        "pas", "asm", // Pascal and ASM, lol
    };

    public static void main(final String[] arguments) throws Exception {
        // Start time.
        long startTime = System.currentTimeMillis();

        // Parse input.
        Map<String, String> config = InputParser.parse(arguments);

        // If debug is enabled, dump config map.
        if (config.get("debug").equals("yes")) {
            System.out.println("Dumping current configuration...");

            for (Entry<String, String> entry: config.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

        String workDir = config.get("dir");

        // Display header.
        System.out.println("Trail (ver. " + VERSION + ") is running...");
        System.out.println("Current working directory is " + workDir);

        Finder finder = new Finder(loadExtensions(workDir));

        for (String filePath: finder.recursiveTraversal(workDir)) {
            if (config.get("verbose").equals("yes")) {
                System.out.println(filePath);
            }
        }

        // Calculate the time.
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("Finished in %.2f seconds.\n", executionTime / 1000.0);
    }

    private static List<String> loadExtensions(final String srcDir) throws Exception {
        boolean canRead = true;
        File file = null;

        try {
            file = new File(srcDir, CONFIG_FILE);
            canRead = file.canRead();
        } catch (Exception error) {
            canRead = false;
        }

        List<String> extensions = new ArrayList<>();

        if ( ! canRead) {
            for (String extension: DEFAULT_EXTENSIONS) {
                extensions.add(extension);
            }

            return extensions;
        }

        List<String> lines = Reader.listOfLines(file.getPath());
        boolean keepDefault = lines.size() == 0 || lines.get(0).equals("keep");

        if (lines.size() > 0) {
            lines.remove(0);
        }

        if (keepDefault) {
            for (String extension: DEFAULT_EXTENSIONS) {
                extensions.add(extension);
            }
        }

        for (String extension: lines) {
            extensions.add(extension);
        }

        return extensions;
    }
}
