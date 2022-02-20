package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import model.Document;
import model.VersionsManager;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.LatexEditor;
import controller.commands.EditCommand;
import javax.swing.JCheckBoxMenuItem;

public class MainWindow {

	private JFrame frame;
	private JEditorPane editorPane = new JEditorPane();
	private LatexEditor latexEditor;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 * @param latexEditor 
	 */
	public MainWindow(LatexEditor latexEditor) {
		this.latexEditor = latexEditor;
		initializeMainWindow();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeMainWindow() {
		
		frame = createFrame();
		
		JMenuBar menuBar = createMenuBar(frame);
		JMenu menuFile = new JMenu("File");
		
		JMenu menuCommands = new JMenu("Commands");
		JMenuItem addChapter = new JMenuItem("Add chapter");
		JMenu addSection = new JMenu("Add Section...");
		JMenu addEnumerationList = new JMenu("Add enumeration list");
		
		JMenu menuStrategy = new JMenu("Strategy");
		JMenu menuEnable = new JMenu("Enable");
//File menu								
		menuBar.add(menuFile);
		newFileMenuItem(menuFile);
		saveFileMenuItem(menuFile);
		loadFileMenuItem(menuFile, addChapter, menuCommands);
		loadHtmlFileMenuItem(menuFile, menuCommands);
		saveAsMenuItem(menuFile);
		saveAsHtmlMenuItem(menuFile);
		exitMenuItem(menuFile);
//Command menu		
		menuBar.add(menuCommands);
		disableCommandsIfLetterOrArticle(addChapter, menuCommands);
		addChapterMenuItem(addChapter, menuCommands);		
		
		menuCommands.add(addSection);
		addSectionMenuItem(addSection);
		addSubSectionMenuItem(addSection);
		addSubSubSectionMenuItem(addSection);		
		
		menuCommands.add(addEnumerationList);	
		addItemizeMenuItem(addEnumerationList);
		addEnumerateMenuItem(addEnumerationList);
		
		addTableMenuItem(menuCommands);
		addFigureMenuItem(menuCommands);

//Strategy menu		
		menuBar.add(menuStrategy);
		menuStrategy.add(menuEnable);		
		StrategyCheckboxes(menuEnable);
		DisableMenuItem(menuStrategy);
		RollbackMenuItem(menuStrategy);
//Pane
		ScrollPane();
	}
	
	private JFrame createFrame() {
		frame = new JFrame();
		frame.setSize(823, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		return frame;
	}
	
	private JMenuBar createMenuBar(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 805, 26);
		frame.getContentPane().add(menuBar);
		return menuBar;
	}
	
	private void newFileMenuItem(JMenu menuFile) {
		JMenuItem menuItemNewFile = new JMenuItem("New file");
		menuItemNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ChooseTemplate(latexEditor, "main");
				frame.dispose();
			}
		});
		menuFile.add(menuItemNewFile);
	}
	
	private void saveFileMenuItem(JMenu menuFile) {
		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditor.setText(editorPane.getText());
				latexEditor.getController().enact("edit");
			}
		});
		menuFile.add(menuItemSave);
	}
	
	private void loadFileMenuItem(JMenu menuFile, JMenuItem addChapter, JMenu menuCommands ) {
		JMenuItem menuItemLoadFile = new JMenuItem("Load file");
		menuItemLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("tex","TEX");
				
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(filter );
				fileChooser.setCurrentDirectory(new File("."));
				int option = fileChooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = fileChooser.getSelectedFile().toString();
					
					latexEditor.setFilename(filename);
					latexEditor.getController().enact("load");
					menuCommands.setEnabled(true);
					addChapter.setEnabled(true);
					disableCommandsIfLetterOrArticle(addChapter, menuCommands);
					editorPane.setText(latexEditor.getCurrentDocument().getContents());
				}
			}
		});
		menuFile.add(menuItemLoadFile);
	}
	
	private void loadHtmlFileMenuItem(JMenu menuFile, JMenu menuCommands) {
		JMenuItem menuItemLoadHtmlFile = new JMenuItem("Load Html file...");
		menuItemLoadHtmlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("html","HTML");
				
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(filter );
				int option = fileChooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = fileChooser.getSelectedFile().toString();
					latexEditor.setFilename(filename);
					latexEditor.getController().enact("loadHtml");
					editorPane.setText(latexEditor.getCurrentDocument().getContents());
					menuCommands.setEnabled(false);
				}
			}
		});
		menuFile.add(menuItemLoadHtmlFile);
	}
	
	private void saveAsMenuItem(JMenu menuFile) {
		JMenuItem menuItemSaveFile = new JMenuItem("Save as...");
		menuItemSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				int option = filechooser.showSaveDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = filechooser.getSelectedFile().toString();
					if(filename.endsWith(".tex") == false) {
						filename = filename+".tex";
					}
					latexEditor.setFilename(filename);
					latexEditor.getController().enact("save");
				}
			}
		});		
		menuFile.add(menuItemSaveFile);
	}
	

	private void saveAsHtmlMenuItem(JMenu menuFile) {
		JMenuItem menuItemSaveHtml = new JMenuItem("Save as html..");
		menuItemSaveHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				int option = filechooser.showSaveDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = filechooser.getSelectedFile().toString();
					if(filename.endsWith(".html") == false) {
						filename = filename+".html";
					}
					latexEditor.setFilename(filename);
					latexEditor.getController().enact("saveHtml");
				}
			}
		});		
		menuFile.add(menuItemSaveHtml);
	}
	
	private void exitMenuItem(JMenu menuFile) {
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(menuItemExit);
	}
		
	private void disableCommandsIfLetterOrArticle(JMenuItem addChapter, JMenu menuCommands) {
		if(latexEditor.getType().equals("letterTemplate")) {
			menuCommands.setEnabled(false);
		}
		if(latexEditor.getType().equals("articleTemplate")) {
			addChapter.setEnabled(false);
		}
	}
	
	private void addChapterMenuItem(JMenuItem addChapter, JMenu menuCommands) {
		addChapter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditCommand.editContents("chapter", editorPane);
			}
		});
		menuCommands.add(addChapter);
	}
	
	private void addSectionMenuItem(JMenu addSection) {
		JMenuItem menuItemAddSection = new JMenuItem("Add section");
		menuItemAddSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("section", editorPane);
			}
		});
		addSection.add(menuItemAddSection);
	}
	
	private void addSubSectionMenuItem(JMenu addSection) {
		JMenuItem menuItemAddSubsection = new JMenuItem("Add subsection");
		menuItemAddSubsection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("subsection", editorPane);
			}
		});
		addSection.add(menuItemAddSubsection);
	}
	
	private void addSubSubSectionMenuItem(JMenu addSection) {
		JMenuItem menuItemAddSubsubsection = new JMenuItem("Add subsubsection");
		menuItemAddSubsubsection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("subsubsection", editorPane);
			}
		});
		addSection.add(menuItemAddSubsubsection);
	}
	
	private void addItemizeMenuItem(JMenu addEnumerationList) {
		JMenuItem menuItemItemize = new JMenuItem("Itemize");
		menuItemItemize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("itemize", editorPane);
			}
		});
		addEnumerationList.add(menuItemItemize);
	}
	
	private void addEnumerateMenuItem(JMenu addEnumerationList) {
		JMenuItem menuItemEnumerate = new JMenuItem("Enumerate");
		menuItemEnumerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("enumerate", editorPane);
			}
		});
		addEnumerationList.add(menuItemEnumerate);
	}
	
	private void addTableMenuItem(JMenu menuCommands) {
		JMenuItem addTable = new JMenuItem("Add table");
		addTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("table", editorPane);
			}
		});
		menuCommands.add(addTable);
	}
	
	private void addFigureMenuItem(JMenu menuCommands) {
		JMenuItem addFigure = new JMenuItem("Add figure");
		addFigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCommand.editContents("figure", editorPane);
			}
		});
		menuCommands.add(addFigure);
	}
	
	private void StrategyCheckboxes(JMenu menuEnable) {
		JCheckBoxMenuItem menuVolatile = new JCheckBoxMenuItem("Volatile");
		JCheckBoxMenuItem menuStable = new JCheckBoxMenuItem("Stable");
		
		menuStable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditor.setStrategy("stable");
				if(VersionsManager.getVersionManager().isEnabled() == false) {
					latexEditor.getController().enact("enableVersionsManagement");
				}
				else {
					latexEditor.getController().enact("changeVersionsStrategy");
				}
				menuVolatile.setSelected(false);
				menuStable.setEnabled(false);
				menuVolatile.setEnabled(true);
			}
		});

		menuVolatile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				latexEditor.setStrategy("volatile");
				if(VersionsManager.getVersionManager().isEnabled() == false) {
					latexEditor.getController().enact("enableVersionsManagement");
				}
				else {
					latexEditor.getController().enact("changeVersionsStrategy");
				}
				menuStable.setSelected(false);
				menuVolatile.setEnabled(false);
				menuStable.setEnabled(true);
			}
		});
		menuEnable.add(menuVolatile);
		menuEnable.add(menuStable);
	}
	
	private void DisableMenuItem(JMenu menuStrategy) {
		JMenuItem menuItemDisable = new JMenuItem("Disable");
		menuItemDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditor.getController().enact("disableVersionsManagement");
			}
		});
		menuStrategy.add(menuItemDisable);
	}
	
	private void RollbackMenuItem(JMenu menuStrategy) {
		JMenuItem menuItemRollback = new JMenuItem("Rollback");
		menuItemRollback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				latexEditor.getController().enact("rollbackToPreviousVersion");
				Document doc = latexEditor.getCurrentDocument();
				editorPane.setText(doc.getContents());
			}
		});
		menuStrategy.add(menuItemRollback);
	}
	
	private void ScrollPane(){
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 783, 467);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(editorPane);
		editorPane.setText(latexEditor.getCurrentDocument().getContents());
	}
	
	public void setEditorPane(String text) {
		editorPane.setText(text);
	}
}
