package trail;

import trail.input.InputParser;
import trail.filesystem.Finder;
import trail.filesystem.Reader;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

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

    // 1) Display the header.
    // 2) Load custom extensions list or use the default one.
    // 3) Create a new instance of Finder and traverse the CWD.
    // 4) If file is "broken", display a message.
    // 5) If "autofix" mode is enabled, "fix" the file.
    // 6) End, show the execution time.
    public static void main(final String[] args) throws Exception {
        // Start time.
        long startTime = System.currentTimeMillis();
        // Current working directory.
        String currentDir = System.getProperty("user.dir");

        // Display header.
        System.out.println("Trail (ver. " + VERSION + ") is running...");
        System.out.println("Current working directory is " + currentDir);

        Finder finder = new Finder(loadExtensions(currentDir));

        for (String filePath: finder.recursiveTraversal(currentDir)) {
            System.out.println(filePath);
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
