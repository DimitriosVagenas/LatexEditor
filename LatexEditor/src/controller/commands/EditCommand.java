package controller.commands;

import javax.swing.JEditorPane;

import controller.LatexEditor;
import model.VersionsManager;

public class EditCommand implements Command {	
	
	private static LatexEditor latexEditor = VersionsManager.getVersionManager().getEditor();
	
	public EditCommand() {
		super();
	}

	@Override
	public void execute() {
		saveContents();
	}
	
	public void saveContents() {
		if(VersionsManager.getVersionManager().isEnabled()) {
			VersionsManager.getVersionManager().putVersion(latexEditor.getCurrentDocument());
			latexEditor.getCurrentDocument().changeVersion();
		}
		latexEditor.getCurrentDocument().setContents(latexEditor.getText());
	}
	
	public static void editContents(String type, JEditorPane editorPane) {
		String contents = editorPane.getText();
		String before = contents.substring(0, editorPane.getCaretPosition());
		String after = contents.substring(editorPane.getCaretPosition());
		
		if(type.equals("chapter")) {
			contents = addChapter(before, after);
		}
		else if(type.equals("section")) {
			contents = addSection(before, after);
		}
		else if(type.equals("subsection")) {
			contents = addSubSection(before, after);
		}
		else if(type.equals("subsubsection")) {
			contents = addSubSubSection(before, after);
		}
		else if(type.equals("enumerate")) {
			contents = addEnumerate(before, after);
		}
		else if(type.equals("itemize")) {
			contents = addItemize(before, after);
		}
		else if(type.equals("table")) {
			contents = addTable(before, after);
		}
		else if(type.equals("figure")) {
			contents = addFigure(before, after);
		}
		latexEditor.setText(contents);
		latexEditor.getController().enact("addLatex");
		editorPane.setText(contents);
	}
	
	private static String addChapter(String before, String after) {
		return before + "\n\\chapter{...}"+"\n"+after;
	}
	
	private static String addSection(String before, String after) {
		return before + "\n\\section{...}"+"\n"+after;
	}
	
	private static String addSubSection(String before, String after) {
		return before + "\n\\subsection{...}"+"\n"+after;
	}
	
	private static String addSubSubSection(String before, String after) {
		return before + "\n\\subsubsection{...}"+"\n"+after;
	}
	
	private static String addEnumerate(String before, String after) {
		return before + 
				"\\begin{enumerate}\n"+
				"\\item ...\n"+
				"\\item ...\n"+
				"\\end{enumerate}\n" + after;
	}
	
	private static String addItemize(String before, String after) {
		return before + 
				"\\begin{itemize}\n"+
				"\\item ...\n"+
				"\\item ...\n"+
				"\\end{itemize}\n" + after;
	}
	
	private static String addTable(String before, String after) {
		return before + 
				"\\begin{table}\n"+
				"\\caption{....}\\label{...}\n"+
				"\\begin{tabular}{|c|c|c|}\n"+
				"\\hline\n"+
				"... &...&...\\\\\n"+
				"... &...&...\\\\\n"+
				"... &...&...\\\\\n"+
				"\\hline\n"+
				"\\end{tabular}\n"+
				"\\end{table}\n" + after;
	}
	private static String addFigure(String before, String after) {
		return before + 
				"\\begin{figure}\n"+
				"\\includegraphics[width=...,height=...]{...}\n"+
				"\\caption{....}\\label{...}\n"+
				"\\end{figure}\n" + after;
	}
	

}
