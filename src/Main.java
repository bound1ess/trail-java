package trail;

import trail.input.ArgumentParser;
import trail.filesystem.Finder;

public class Main {
    private final static String VERSION = "development"; // @todo

    // 1) Display the header.
    // 2) Load custom extensions list or use the default one.
    // 3) Create a new instance of Finder and traverse the CWD.
    // 4) If file is "broken", display a message.
    // 5) If "autofix" mode is enabled, "fix" the file.
    // 6) End, show the execution time.
    public static void main(final String[] args) {
        // Start time.
        long startTime = System.currentTimeMillis();

        // Display header.
        System.out.println("Trail (ver. " + VERSION + ") is running...");
        System.out.println("Current working directory is " + System.getProperty("user.dir"));

        String[] extensions = {"txt", "doc", "java"};
        Finder finder = new Finder(extensions);

        for (String filePath: finder.recursiveTraversal(System.getProperty("user.dir"))) {
            System.out.println(filePath);
        }

        // Calculate the time.
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("Finished in %.2f seconds.\n", executionTime / 1000.0);
    }
}
