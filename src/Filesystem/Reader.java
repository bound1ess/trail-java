package trail.filesystem;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Reader {

    public static List<String> listOfLines(final String src) throws Exception {
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
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        return lines;
    }
}
