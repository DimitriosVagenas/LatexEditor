package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.LatexEditor;
import model.VersionsManager;

public class LoadCommand implements Command {
	
	private LatexEditor latexEditor = VersionsManager.getVersionManager().getEditor();
	
	public LoadCommand() {
		super();
	}

	@Override
	public void execute() {
		loadFromFile();
	}
	
	public void loadFromFile() {
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
		
		latexEditor.setType("emptyTemplate");
		
		fileContents = fileContents.trim();
		if(fileContents.startsWith("\\documentclass[11pt,twocolumn,a4paper]{article}")) {
			latexEditor.setType("articleTemplate");
		}
		else if(fileContents.startsWith("\\documentclass[11pt,a4paper]{book}")) {
			latexEditor.setType("bookTemplate");
		}
		else if(fileContents.startsWith("\\documentclass[11pt,a4paper]{report}")) {
			latexEditor.setType("reportTemplate");
		}
		else if(fileContents.startsWith("\\documentclass{letter}")) {
			latexEditor.setType("letterTemplate");
		}
	}
	
}
