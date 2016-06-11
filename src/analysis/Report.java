package analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import repository.Repository;
import util.InfoFile;

public class Report {

    private static final String CHARSET = "UTF-8";
    private static final String REPORT_NAME = "report.txt";

    private Report() {
    }

    public static void write(InfoFile infoFile) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(REPORT_NAME), CHARSET);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Error to create writer to write report");
            return;
        }

        for (Repository repo : infoFile.getAllRepos()) {
            writer.println("url = " + repo.getUri());

            for (AnalysisUnit unit : infoFile.getAllAnalysisUnit(repo)) {
                writer.println(unit.getResult() + " [" + unit.getCommit1() + "," + unit.getFilename1() + ","
                        + unit.getFirstline() + "," + unit.getLastline() + "," + unit.getCommit2() + ","
                        + unit.getFilename2() + "]");
            }
        }

        writer.close();
    }

}
