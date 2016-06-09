package util;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class CloneRepo {

    private static final String REPO_DIRECTORY = "./repos";

    public CloneRepo(String uri) {

        String repoName = uri.substring(uri.lastIndexOf('/') + 1);

        File folder = new File(REPO_DIRECTORY + '/' + repoName);

        if (!(folder.exists())) {
            Git git = null;
            try {
                git = Git.cloneRepository().setURI(uri).setDirectory(folder).call();
            } catch (GitAPIException e) {
                System.out.println("error to get git "+repoName+" repository");
            }

            git.close();
            System.out.println("git repository "+repoName+" clonned");
        } else {
            System.out.println("git repository "+repoName+" already exists");
        }

    }

}
