package trail;

import java.util.List;
import java.util.ArrayList;

public class Validator {

    public static List<String> fixBrokenLines(final List<String> lines) {
        List<String> newLines = new ArrayList<>();
        String separator = System.lineSeparator();

        for (String line: lines) {
            newLines.add(rightTrim(line) + separator);
        }

        return newLines;
    }

    public static List<Integer> findBrokenLines(final List<String> lines) {
        int extra = System.lineSeparator().length(), lineNumber = 1;
        List<Integer> brokenLines = new ArrayList<>();

        for (String line: lines) {
            if (line.length() > rightTrim(line).length() + extra) {
                brokenLines.add(lineNumber);
            }

            lineNumber++;
        }

        return brokenLines.size() > 0 ? brokenLines : null;
    }

    private static String rightTrim(final String line) {
        int position = line.length() - 1;

        while (position >= 0 && (int) line.charAt(position) <= 32) {
            position--;
        }

        return line.substring(0, position + 1);
    }
}
