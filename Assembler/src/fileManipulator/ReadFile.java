package fileManipulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import validation.Exceptions;

public class ReadFile {

	public ArrayList<String> readSrcFile(File file) {
		ArrayList<String> lines = new ArrayList<>();
		try (Scanner scan = new Scanner(file);) {

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			Exceptions.getInstance().throwCouldNotReadException();
		}
		return lines;
	}
}
