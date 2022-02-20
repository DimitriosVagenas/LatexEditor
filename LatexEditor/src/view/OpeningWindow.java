package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.LatexEditor;
import controller.LatexEditorController;
import model.VersionsManager;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class OpeningWindow {

	private JFrame frame;
	private LatexEditor latexEditor;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpeningWindow window = new OpeningWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpeningWindow() {
		VersionsStrategy versionsStrategy = new VolatileVersionsStrategy();
		latexEditor = new LatexEditor();
		new VersionsManager(versionsStrategy, latexEditor);
		LatexEditorController controller = new LatexEditorController();
		latexEditor.setController(controller);
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createFrame();
		createButtonCreateNewDocument();
		createButtonOpenExistingDocument();
		createButtonExit();
	}
	
	private void createFrame() {
		frame = new JFrame();
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
	}
	
	private void createButtonCreateNewDocument() {
		JButton buttonCreateNewDocument = new JButton("Create New Document");
		buttonCreateNewDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChooseTemplate(latexEditor, "opening");
				frame.dispose();
			}
		});
		buttonCreateNewDocument.setBounds(89, 26, 278, 36);
		frame.getContentPane().add(buttonCreateNewDocument);
	}
	
	private void createButtonOpenExistingDocument() {
		JButton buttonOpenExistingDocument = new JButton("Open Existing Document");
		buttonOpenExistingDocument.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditor.setType("emptyTemplate");
				latexEditor.getController().enact("create");
				frame.dispose();
				MainWindow mainWindow = new MainWindow(latexEditor);
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("tex","TEX");
				
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(filter );
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setCurrentDirectory(new File("."));
				int option = fileChooser.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filename = fileChooser.getSelectedFile().toString();
					latexEditor.setFilename(filename);
					latexEditor.getController().enact("load");
					mainWindow.setEditorPane(latexEditor.getCurrentDocument().getContents());
			
				}
			}
		});
		
		buttonOpenExistingDocument.setBounds(89, 92, 278, 36);
		frame.getContentPane().add(buttonOpenExistingDocument);
	}
	
	private void createButtonExit() {
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonExit.setBounds(89, 169, 278, 36);
		frame.getContentPane().add(buttonExit);
	}
}
