package trail;

import trail.input.ArgumentParser;
import trail.filesystem.Finder;

public class Main {
    private final static String VERSION = "development"; // @TODO

    public static void main(final String[] args) {
        // Display header.
        System.out.println("Trail (ver. " + VERSION + ") is running...");
        System.out.println("Current working directory is " + System.getProperty("user.dir"));

        String[] extensions = {"txt", "doc", "java"};
        Finder finder = new Finder(extensions);

        for (String filePath: finder.recursiveTraversal(System.getProperty("user.dir"))) {
            System.out.println(filePath);
        }
    }
}
