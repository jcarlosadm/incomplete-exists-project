package analysis;

import java.util.List;

import file.FileLineExplorer;
import repository.Repository;
import util.InfoFile;

public class AnalysisRunner {

    private AnalysisRunner() {
    }

    public static void run() {
        InfoFile infoFile = InfoFile.getInfoFile();

        for (Repository repo : infoFile.getAllRepos()) {
            repo.cloneRepository();
            for (AnalysisUnit unit : infoFile.getAllAnalysisUnit(repo)) {
                unit.setResult(analyze(unit, repo));
            }
            repo.checkoutMaster();
        }

        Report.write(infoFile);
    }

    private static String analyze(AnalysisUnit unit, Repository repo) {
        if (!repo.checkout(unit.getCommit1()))
            return "checkout failed";
        String repoDir = Repository.getRepoDirectory();
        List<String> lines = FileLineExplorer.getLines(
                repoDir + System.getProperty("file.separator") + unit.getFilename1(), unit.getFirstline(),
                unit.getLastline());

        if (!repo.checkout(unit.getCommit2()))
            return "checkout failed";

        if (FileLineExplorer.searchLines(repoDir + System.getProperty("file.separator") + unit.getFilename2(),
                lines) == true) {
            return "match";
        }

        return "not match";
    }

    public static void main(String[] args) {
        AnalysisRunner.run();
    }
}
