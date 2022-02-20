package controller;

import java.util.HashMap;

import controller.commands.Command;
import controller.commands.CommandFactory;

public class LatexEditorController{
	private HashMap<String, Command> commands;
	
	public LatexEditorController() {
		CommandFactory commandFactory = new CommandFactory();
		String[] commandNames = {"addLatex","changeVersionsStrategy", "create", "disableVersionsManagement", "edit", "enableVersionsManagement", "load", "rollbackToPreviousVersion", "save", "loadHtml", "saveHtml"};  
		commands = new HashMap<String, Command>();
		for(String name:commandNames){
			commands.put(name, commandFactory.createCommand(name));
		}
	}
	
	public void enact(String command) {
		commands.get(command).execute();
	}
}
