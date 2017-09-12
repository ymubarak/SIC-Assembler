package fileManipulator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import runner.Memento;
import validation.Exceptions;

public class WriteFile {

	private Memento memnto;

	public WriteFile() {
		memnto = new Memento();
	}

	public void writeIntermediateFile(File file, String cPath, String intermedFileContent) {
		PrintWriter writer = null;
		String fileName = "\\" + file.getName().substring(0, file.getName().indexOf('.')) + "intermed.asm";
		try {
			writer = new PrintWriter(cPath + fileName, "UTF-8");
			writer.write(intermedFileContent.toString());
			writer.close();
			memnto.setListFile(cPath + fileName);

		} catch (IOException e) {
			Exceptions.getInstance().throwCouldNotWriteException();
		} finally {
			writer.close();
		}
	}

	public void writeObjFile(File file, String cPath, String objFileContent) {
		PrintWriter writer = null;
		String fileName = "\\" + file.getName().substring(0, file.getName().indexOf('.')) + ".o";
		try {
			writer = new PrintWriter(cPath + fileName, "UTF-8");
			writer.write(objFileContent.toString());
			writer.close();
			memnto.setObjectFile(cPath + fileName);
		} catch (IOException e) {
			Exceptions.getInstance().throwCouldNotWriteException();
		} finally {
			writer.close();
		}
	}
}
