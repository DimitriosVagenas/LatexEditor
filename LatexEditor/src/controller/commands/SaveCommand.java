package controller.commands;

import controller.LatexEditor;
import model.VersionsManager;

public class SaveCommand implements Command {
	
	private LatexEditor latexEditor = VersionsManager.getVersionManager().getEditor();
	
	@Override
	public void execute() {
		saveToFile();
	}

	public void saveToFile() {
		latexEditor.getCurrentDocument().save(latexEditor.getFilename()); 
	}
	
}
