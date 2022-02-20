package controller.commands;

import model.VersionsManager;

public class DisableVersionsManagementCommand implements Command {
	
	public DisableVersionsManagementCommand() {
		super();
	}

	@Override
	public void execute() {
		VersionsManager.getVersionManager().disable();
	}
	
}
