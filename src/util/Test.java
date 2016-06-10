package util;

import repository.Repository;

public class Test {
	public static void main(String[] args) {
	    Repository repository = new Repository("https://github.com/mate-desktop/pluma");
	    repository.cloneRepository();
	}
}
