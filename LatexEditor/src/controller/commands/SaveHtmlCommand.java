package controller.commands;

import controller.LatexEditor;
import model.VersionsManager;

public class SaveHtmlCommand implements Command {

	private LatexEditor latexEditor = VersionsManager.getVersionManager().getEditor();
	
	@Override
	public void execute() {
		saveHtmlFile();
	}

	public void saveHtmlFile() {
		latexEditor.getCurrentDocument().save(latexEditor.getFilename()); 
	}
	
}
