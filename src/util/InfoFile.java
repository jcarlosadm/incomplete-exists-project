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

import repository.AnalysisUnit;
import repository.Repository;

public class InfoFile {

    private static final String PREFIX_ANALYSISUNIT = "[";

    private static final String INFOFILE_PATH = "infofile";

    private static InfoFile infoFile = null;

    private Map<Repository, List<AnalysisUnit>> repoMap = new HashMap<Repository, List<AnalysisUnit>>();

    private InfoFile() {
        File file = new File(INFOFILE_PATH);

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));

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
        
        AnalysisUnit unit = new AnalysisUnit();
        unit.setFilename(this.removeWhiteSpaces(parts[0].substring(1, parts[0].length() - 1)));
        unit.setCommit1(this.removeWhiteSpaces(parts[1]));
        unit.setCommit2(this.removeWhiteSpaces(parts[4].substring(0, parts[4].length() - 2)));
        unit.setFirstline(Integer.parseInt(this.removeWhiteSpaces(parts[2])));
        unit.setLastline(Integer.parseInt(this.removeWhiteSpaces(parts[3])));
        
        List<AnalysisUnit> list = this.repoMap.get(repo);
        list.add(unit);
    }
    
    private String removeWhiteSpaces(String string){
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
