package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import fileManipulator.ReadFile;
import runner.Adapter;
import runner.Memento;
import validation.Exceptions;

public class Listner implements ActionListener {

	private AssemblerPanel assemPanel;
	private AssemblerFrame assemFrame;
	private Adapter adapter;
	private File currentFile;
	private ReadFile readFile;
	private Memento memnto;
	private boolean unsavedWork = false;

	public Listner(AssemblerFrame f, AssemblerPanel p) {
		assemFrame = f;
		assemPanel = p;
		adapter = new Adapter();
		readFile = new ReadFile();
		memnto = new Memento();
	}

	private File getFile(String label) {
		JFileChooser test = new JFileChooser();
		test.showDialog(null, label);
		File file = test.getSelectedFile();
		if (file == null)
			return null;

		return file;
	}

	private void setAreaContent(ArrayList<String> contentList) {
		String content = contentList.get(0);
		for (int i = 1; i < contentList.size(); i++) {
			content += "\n";
			content += contentList.get(i);
		}
		assemPanel.area.setText(content);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object s = event.getSource();
		if (s == assemFrame.newF) {
			// this
			unsavedWork = true;
			assemPanel.area.setText("");
			File newFile = new File(getFile("Add New File").getAbsolutePath() + ".asm");
			try {
				newFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			currentFile = newFile;
			assemPanel.currentPath.setText("Path: " + currentFile.getAbsolutePath());

		} else if (s == assemFrame.open) {
			currentFile = getFile("Load");
			setAreaContent(readFile.readSrcFile(currentFile));
			assemPanel.currentPath.setText("Path: " + currentFile.getAbsolutePath());

		} else if (s == assemFrame.save) {
			if (currentFile != null) {
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(currentFile, "UTF-8");
					writer.write(assemPanel.area.getText().toString());
					writer.close();
					unsavedWork = false;
				} catch (IOException e) {
					Exceptions.getInstance().throwCouldNotWriteException();
				} finally {
					writer.close();
				}
			}

		} else if (s == assemFrame.exit) {
			System.exit(0);
		} else if (s == assemFrame.assemble) {
			if (!unsavedWork) {
				if (currentFile == null) {
					JOptionPane.showMessageDialog(null, " No File is specified ! \n\n", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int reply = JOptionPane.showConfirmDialog(null, " Assemble Displayed File? \n\n", "Delete",
							JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						adapter.assemble(currentFile);
						setAreaContent(readFile.readSrcFile(currentFile));
						assemPanel.currentPath.setText("Path: " + currentFile.getAbsolutePath());
						JOptionPane.showMessageDialog(null, " Successful assembly \n\n", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, " Warning ! , Unsaved Work \n\n", "Warning",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (s == assemFrame.objectFile) {
			if (memnto.getObjectFile() != null) {
				setAreaContent(readFile.readSrcFile(memnto.getObjectFile()));
				assemPanel.currentPath.setText("Path: " + memnto.getObjectFile());
			} else {
				JOptionPane.showMessageDialog(null, " Warning ! , No Object file is found \n\n", "Warning",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} else if (s == assemFrame.listFile) {
			if (memnto.getListFile() != null) {
				setAreaContent(readFile.readSrcFile(memnto.getListFile()));
				assemPanel.currentPath.setText("Path: " + memnto.getListFile());
			} else {
				JOptionPane.showMessageDialog(null, " Warning ! , No List file is found \n\n", "Warning",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}