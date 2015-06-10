package trail;

import java.util.List;
import java.util.ArrayList;

public class Validator {

    public static List<String> fix(final List<String> lines) {
        List<String> newLines = new ArrayList<>();

        for (String line: lines) {
            newLines.add(rightTrim(line));
        }

        return newLines;
    }

    public static boolean isBroken(final List<String> lines) {
        for (String line: lines) {
            if ( ! line.isEmpty() && Character.isWhitespace(line.charAt(line.length() - 1))) {
                return true;
            }
        }

        return false;
    }

    private static String rightTrim(final String line) {
        int position = line.length() - 1;

        while (position >= 0 && Character.isWhitespace(line.charAt(position))) {
            position--;
        }

        return line.substring(0, position + 1);
    }
}
