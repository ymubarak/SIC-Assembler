package runner;

import java.io.File;

public class Memento {

	private static File objectFile;
	private static File listFile;

	public void setObjectFile(String path) {
		objectFile = new File(path);
	}

	public void setListFile(String path) {
		listFile = new File(path);
	}

	public File getObjectFile() {
		return objectFile;
	}

	public File getListFile() {
		return listFile;
	}

}
