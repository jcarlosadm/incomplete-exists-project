package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import repository.AnalysisUnit;
import repository.Repository;

public class InfoFile {

    private static InfoFile infoFile = null;

    private Map<Repository, List<AnalysisUnit>> repoMap = new HashMap<Repository, List<AnalysisUnit>>();

    private InfoFile() {
        
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
