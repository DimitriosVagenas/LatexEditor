package controller.commands;

import model.VersionsManager;

public class RollbackToPreviousVersionCommand implements Command {
	
	public RollbackToPreviousVersionCommand() {
		super();
	}
	
	@Override
	public void execute() {
		VersionsManager.getVersionManager().rollback();
	}

}
