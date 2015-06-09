package trail.input;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class InputParser {

    public static Map<String, String> parse(final String[] arguments) {
        Map<String, String> config = new HashMap<>();
        Queue<String> input = new LinkedList<>();

        for (String argument: arguments) {
            input.add(argument);
        }

        // Default values.
        config.put("dir", System.getProperty("user.dir")); // work with CWD
        config.put("fix", "no"); // DO NOT change any files
        config.put("verbose", "no"); // DO NOT show processed files
        config.put("debug", "no"); // DO NOT show debug information

        if ( ! input.isEmpty()) {
            String srcDir = input.poll();

            if ( ! srcDir.startsWith("/")) {
                srcDir = new File(config.get("dir"), srcDir).getPath();
            }

            config.put("dir", srcDir);
        }

        if ( ! input.isEmpty()) {
            config.put("fix", input.poll().equals("fix") ? "yes" : "no");
        }

        while ( ! input.isEmpty()) {
            switch (input.poll()) {
                case "--verbose":
                    config.put("verbose", "yes");

                case "--debug":
                    config.put("debug", "yes");

                default:
                    throw new IllegalArgumentException("Unrecognized input option.");
            }
        }

        return config;
    }
}
