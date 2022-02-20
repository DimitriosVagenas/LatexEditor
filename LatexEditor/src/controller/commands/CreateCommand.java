package controller.commands;

import model.Document;
import model.DocumentManager;
import model.VersionsManager;

public class CreateCommand implements Command {
	
	public CreateCommand() {
		super();
	}

	@Override
	public void execute() {
		String type = VersionsManager.getVersionManager().getEditor().getType(); //get type middle man
		Document document = DocumentManager.getDocumentManager().createDocument(type);
		VersionsManager.getVersionManager().setCurrentVersion(document);
	}

}
