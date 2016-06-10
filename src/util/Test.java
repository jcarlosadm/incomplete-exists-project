package util;

import repository.Repository;

public class Test {
	public static void main(String[] args) {
	    Repository repository = new Repository("https://github.com/mate-desktop/pluma");
	    repository.cloneRepository();
	    //repository.checkout("4e00171156b92654b7f509e0016a21654e98b3a6");
	}
}
