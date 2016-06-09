package repository;

public class Repository {

    private String name = "";

    private String uri = "";

    public Repository(String uri) {
        this.uri = uri;
        this.name = uri.substring(uri.lastIndexOf('/') + 1);
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

}
