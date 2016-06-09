package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import repository.AnalysisUnit;
import repository.Repository;

public class InfoFile {

    private static final String INFOFILE_PATH = "infofile";

    private static InfoFile infoFile = null;

    private Map<Repository, List<AnalysisUnit>> repoMap = new HashMap<Repository, List<AnalysisUnit>>();

    private InfoFile() {
        File file = new File(INFOFILE_PATH);

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            
            for(String line; (line = bufferedReader.readLine()) != null; ) {
                // TODO process the line
            }
        } catch (IOException e) {
            System.out.println("error to read infofile");
        }

        // TODO build repoMap
    }

    public static InfoFile loadResultFile() {
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
