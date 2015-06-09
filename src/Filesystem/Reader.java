package trail.filesystem;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Reader {

    public static List<String> listOfLines(final String src) {
        File file = null;

        try {
            file = new File(src);

            if ( ! file.canRead()) {
                return null;
            }
        } catch (Exception error) {
            return null;
        }

        List<String> lines = new ArrayList<>();

        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (Exception error) {
            return null;
        }

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        return lines;
    }
}
