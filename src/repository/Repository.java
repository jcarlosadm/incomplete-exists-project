package repository;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

public class Repository {

    private static final String REPO_DIRECTORY = "repos";

    private String name = "";

    private String uri = "";

    private File directory = null;

    private Git git = null;

    public Repository(String uri) {
        this.uri = uri;
        this.name = uri.substring(uri.lastIndexOf('/') + 1);

        this.directory = new File(REPO_DIRECTORY + System.getProperty("file.separator") + name);
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public void cloneRepository() {
        if (git != null)
            return;

        if (this.directory.exists() && this.directory.isDirectory()) {
            try {
                this.git = Git.open(new File(this.directory.getPath() + System.getProperty("file.separator") + ".git"));
            } catch (IOException e) {
                System.out.println("error to set git folder");

            }
            return;
        }

        try {
            this.git = Git.cloneRepository().setURI(this.uri).setDirectory(this.directory).call();
        } catch (GitAPIException e) {
            System.out.println("error to clone repository");
        }
    }
    
    public boolean checkout(String hash) {
        try {
            this.git.checkout().setName(hash).call();
            return true;
        } catch(Exception e) {
            System.out.println("Não foi possível dar o checkout no commit: " + hash + ", resetando para tentar de novo");
        }
        try {
            git.reset().setMode(ResetCommand.ResetType.HARD).call();
            git.checkout().setName(hash).call();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível dar o checkout no commit: " + hash);
            return false;
        }
    }
    
    public boolean checkoutMaster(){
        return this.checkout("master");
    }

}
