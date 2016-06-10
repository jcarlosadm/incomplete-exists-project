package repository;

public class AnalysisUnit {
    
    private String filename = "";

    private String commit1 = "";

    private String commit2 = "";

    private int firstline = 0;

    private int lastline = 0;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCommit1() {
        return commit1;
    }

    public void setCommit1(String commit1) {
        this.commit1 = commit1;
    }

    public String getCommit2() {
        return commit2;
    }

    public void setCommit2(String commit2) {
        this.commit2 = commit2;
    }

    public int getFirstline() {
        return firstline;
    }

    public void setFirstline(int firstline) {
        this.firstline = firstline;
    }

    public int getLastline() {
        return lastline;
    }

    public void setLastline(int lastline) {
        if (lastline >= this.firstline) {
            this.lastline = lastline;
        } else {
            System.out.println("last line " + lastline + " smaller than first line");
        }
    }

}
