package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.LatexEditor;
import model.VersionsManager;

public class LoadHtmlCommand implements Command {
	
	private LatexEditor latexEditor = VersionsManager.getVersionManager().getEditor();
	
	public LoadHtmlCommand() {
		super();
	}

	
	@Override
	public void execute() {
		String fileContents = "";
		try {
			Scanner scanner = new Scanner(new FileInputStream(latexEditor.getFilename()));
			while(scanner.hasNextLine()) {
				fileContents = fileContents + scanner.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		latexEditor.getCurrentDocument().setContents(fileContents);
		latexEditor.setType("htmlTemplate");
	}
}


