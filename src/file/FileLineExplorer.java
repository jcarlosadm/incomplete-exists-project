package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLineExplorer {

    private FileLineExplorer() {
    }

    /**
     * Extract the lines in file
     * 
     * @param pathFile
     *            file to extract lines
     * @param firstline
     *            first line
     * @param lastline
     *            last line (it must be equal or greater than first line)
     * @return list of lines. If is null, then lines were not found or there was
     *         a file read error
     */
    public static List<String> getLines(String pathFile, int firstline, int lastline) {
        List<String> lines = new ArrayList<>();

        File file = new File(pathFile);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line = "";
            for (int lineIndex = 0; lineIndex < lastline; lineIndex++) {
                line = bufferedReader.readLine();
                if (lineIndex >= (firstline - 1) && lineIndex <= (lastline - 1)) {
                    lines.add(line);
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("error to read file");
            return null;
        }

        return lines;
    }

    /**
     * Search lines in file
     * 
     * @param pathFile
     *            file to be searched
     * @param lines
     *            lines to search
     * @return true if lines are found. false if otherwise
     */
    public static boolean searchLines(String pathFile, List<String> lines) {
        File file = new File(pathFile);
        int currentOriginalLineIndex = 0;
        boolean someLineFound = false;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            for (String line; (line = bufferedReader.readLine()) != null;) {
                if (lines.get(currentOriginalLineIndex).equals(line)) {
                    currentOriginalLineIndex++;
                    someLineFound = true;
                }
                // the lines are not consecutive!
                else if (someLineFound == true) {
                    currentOriginalLineIndex = 0;
                    someLineFound = false;
                }

                // found all lines
                if (currentOriginalLineIndex >= (lines.size() - 1)) {
                    break;
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("error to read file");
            return false;
        }

        if (currentOriginalLineIndex == (lines.size() - 1)) {
            return true;
        }
        return false;
    }
}
