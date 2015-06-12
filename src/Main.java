package trail;

import trail.input.InputParser;
import trail.filesystem.Finder;
import trail.filesystem.Reader;
import trail.output.OutputWriter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

//import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

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

    public static void main(final String[] arguments) {
        // Start time.
        long startTime = System.currentTimeMillis();

        // Parse input.
        Map<String, String> config = InputParser.parse(arguments);

        String workDir = config.get("dir");

        if ( ! Files.isReadable(Paths.get(workDir))) {
            System.out.println("Directory " + workDir + " does not exist!");
            return;
        }

        // Display header.
        System.out.println("Trail (ver. " + VERSION + ") is running...");
        System.out.println(
            "Current working directory is " + Paths.get(workDir).toAbsolutePath().toString()
        );

        // If debug is enabled, dump config map.
        if (config.get("debug").equals("yes")) {
            System.out.println("Dumping current configuration...");
            System.out.println(config.toString());
        }

        List<String> extensions = loadExtensions(workDir);
        Finder finder = new Finder(extensions);

        // If debug is enabled, print all extensions.
        if (config.get("debug").equals("yes")) {
            System.out.println("Extensions: " + extensions.toString());
        }

        boolean silent = config.get("verbose").equals("no");
        silent = silent && config.get("debug").equals("no");

        boolean fixFiles = config.get("fix").equals("yes");

        if (fixFiles) {
            System.out.println("Fixing broken files...");
        }

        int totalFiles = 0, brokenFiles = 0;

        for (String filePath: finder.recursiveTraversal(Paths.get(workDir))) {
            totalFiles++;

            if ( ! silent) {
                System.out.print("Checking " + filePath + "...");
            }

            List<String> lines = Reader.listOfLines(Paths.get(filePath));
            List<Integer> brokenLines = Validator.findBrokenLines(lines);

            if (brokenLines == null) { // file is OK
                if ( ! silent) {
                    System.out.println(" [OK]");
                }

                continue;
            }

            brokenFiles++;

            if ( ! fixFiles) {
                if (silent) {
                    System.out.println(
                        "File " + filePath + " is broken, lines " + brokenLines.toString()
                    );
                } else {
                    System.out.println(" Broken, lines " + brokenLines.toString());
                }
            } else {
                boolean fixed = OutputWriter.writeList(
                    Paths.get(filePath), Validator.fixBrokenLines(lines)
                );

                if (silent) {
                    System.out.print("File " + filePath);
                    System.out.println(fixed ? " was fixed" : " was not fixed");
                } else {
                    System.out.println(fixed ? " [Fixed]" : " Failed to fix");
                }
            }
        }

        // Print statistics.
        System.out.printf(
            "Total files: %d, good: %d, broken: %d\n",
            totalFiles, (totalFiles - brokenFiles), brokenFiles
        );

        // Calculate the time.
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("Finished in %.2f second(s).\n", executionTime / 1000.0);
    }

    private static List<String> loadExtensions(final String srcDir) {
        Path filePath = Paths.get(srcDir);

        List<String> extensions = new ArrayList<>();
        List<String> lines = Reader.listOfLines(filePath);

        if (lines == null) {
            for (String extension: DEFAULT_EXTENSIONS) {
                extensions.add(extension);
            }

            return extensions;
        }

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
