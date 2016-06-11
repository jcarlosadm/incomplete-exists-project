package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analysis.AnalysisUnit;
import repository.Repository;

public class InfoFile {

    private static final int COMMIT1_INDEX = 0;
    private static final int COMMIT2_INDEX = 4;
    private static final int FILENAME1_INDEX = 1;
    private static final int FILENAME2_INDEX = 5;
    private static final int LINE1_INDEX = 2;
    private static final int LINE2_INDEX = 3;

    private static final String PREFIX_ANALYSISUNIT = "[";

    private static final String INFOFILE_PATH = "infofile";

    private static InfoFile infoFile = null;

    private Map<Repository, List<AnalysisUnit>> repoMap = new HashMap<Repository, List<AnalysisUnit>>();

    private InfoFile() {
        File file = new File(INFOFILE_PATH);
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            Repository repo = null;
            for (String line; (line = bufferedReader.readLine()) != null;) {
                if (!line.isEmpty()) {
                    if (!line.startsWith(PREFIX_ANALYSISUNIT)) {
                        repo = this.addRepository(line);
                    } else if (repo != null) {
                        this.addAnalysisUnit(repo, line);
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("error to read infofile");
        }
    }

    private Repository addRepository(String line) {
        String uri = line.substring(line.lastIndexOf('=') + 1);
        uri = this.removeWhiteSpaces(uri);

        Repository repo = new Repository(uri);
        this.repoMap.put(repo, new ArrayList<AnalysisUnit>());

        return repo;
    }

    private void addAnalysisUnit(Repository repo, String line) {
        String[] parts = line.split(",");
        int lastIndex = parts.length - 1;

        // remove '[' and ']'
        parts[0] = parts[0].substring(1, parts[0].length());
        parts[lastIndex] = parts[lastIndex].substring(0, parts[lastIndex].length() - 1);

        // remove white spaces
        for (int index = 0; index < parts.length; index++) {
            parts[index] = this.removeWhiteSpaces(parts[index]);
        }

        AnalysisUnit unit = new AnalysisUnit();
        // fill unit
        unit.setFilename1(parts[FILENAME1_INDEX]);
        unit.setFilename2(parts[FILENAME2_INDEX]);
        unit.setCommit1(parts[COMMIT1_INDEX]);
        unit.setCommit2(parts[COMMIT2_INDEX]);
        unit.setFirstline(Integer.parseInt(parts[LINE1_INDEX]));
        unit.setLastline(Integer.parseInt(parts[LINE2_INDEX]));

        List<AnalysisUnit> list = this.repoMap.get(repo);
        list.add(unit);
    }

    private String removeWhiteSpaces(String string) {
        return string.replaceAll("\\s+", "");
    }

    public static InfoFile getInfoFile() {
        if (infoFile == null) {
            infoFile = new InfoFile();
        }

        return infoFile;
    }

    public Set<Repository> getAllRepos() {
        return this.repoMap.keySet();
    }

    public List<AnalysisUnit> getAllAnalysisUnit(Repository repository) {
        return this.repoMap.get(repository);
    }

}
