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

        while ( ! input.isEmpty()) {
            String option = input.poll();

            if (option.startsWith("--dir")) {
                String value = parseOptionValue(option);

                if (value == null) {
                    if (input.isEmpty()) {
                        throw new IllegalArgumentException("Value missing (--dir).");
                    }

                    value = removeEscapeChars(input.poll());
                }

                config.put("dir", value);
                continue;
            }

            switch (option) {
                case "--verbose":
                    config.put("verbose", "yes");
                    break;

                case "--debug":
                    config.put("debug", "yes");
                    break;

                case "--fix":
                    config.put("fix", "yes");
                    break;

                default:
                    throw new IllegalArgumentException("Unrecognized option: " + option);
            }
        }

        return config;
    }

    private static String parseOptionValue(final String option) {
        int equalSignIndex = option.indexOf('=');

        if (equalSignIndex == -1) {
            return null; // refer to the next queue element
        }

        return removeEscapeChars(option.substring(equalSignIndex + 1));
    }

    private static String removeEscapeChars(final String value) {
        String a = "\"", b = "'";

        if ((value.startsWith(a) && value.endsWith(a))
            || (value.startsWith(b) && value.endsWith(b))) {

            return value.substring(1, value.length() - 1);
        }

        return value;
    }
}
