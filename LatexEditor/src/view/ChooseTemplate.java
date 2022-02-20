package view;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import controller.LatexEditor;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseTemplate {

	private JFrame frame;
	private LatexEditor latexEditor;
	private String previous;

	/**
	 * Create the application.
	 * @param latexEditor 
	 */
	public ChooseTemplate(LatexEditor latexEditor, String previous) {
		this.latexEditor = latexEditor;
		this.previous = previous;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void diselectRadioButtons(JRadioButton radioButton1, JRadioButton radioButton2, JRadioButton radioButton3,JRadioButton radioButton4) {
		if(radioButton1.isSelected()) {
			radioButton2.setSelected(false);
			radioButton3.setSelected(false);
			radioButton4.setSelected(false);
		}
	}
	
	private void initialize() {
		
		JRadioButton book = new JRadioButton("Book");
		JRadioButton article = new JRadioButton("Article");
		JRadioButton report = new JRadioButton("Report");
		JRadioButton letter = new JRadioButton("Letter");
		
		createFrame();
		createLabel();
		
		BookRadioButton(book, article, report, letter);
		ArticleRadioButton(article, report, letter, book);
		ReportRadioButton(report, letter, book, article);
		LetterRadioButton(letter, book, article, report);
		
		CreateButton(book, article, report, letter);
		BackButton();
	}
	
	private void createFrame(){
		frame = new JFrame();
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
	}
	
	private void createLabel() {
		JLabel lblChooseTemplate = new JLabel("Choose template. (Leave empty for blank document)");
		lblChooseTemplate.setBounds(42, 13, 332, 16);
		frame.getContentPane().add(lblChooseTemplate);
	}
	
	private void BookRadioButton(JRadioButton book, JRadioButton article, JRadioButton report, JRadioButton letter) {
		book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diselectRadioButtons(book, article, report, letter);
			}
		});
		book.setBounds(42, 51, 127, 25);
		frame.getContentPane().add(book);
	}
	
	private void ArticleRadioButton(JRadioButton article, JRadioButton report, JRadioButton letter, JRadioButton book) {
		article.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diselectRadioButtons(article, book, report, letter);

			}
		});
		article.setBounds(42, 137, 127, 25);
		frame.getContentPane().add(article);	
	}
	
	private void ReportRadioButton(JRadioButton report, JRadioButton letter, JRadioButton book, JRadioButton article) {
		report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diselectRadioButtons(report, article, book, letter);
				
			}
		});
		report.setBounds(213, 51, 127, 25);
		frame.getContentPane().add(report);
	}
	
	private void LetterRadioButton(JRadioButton letter, JRadioButton book, JRadioButton article, JRadioButton report) {
		letter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diselectRadioButtons( letter, report, article, book);
			}
		});
		letter.setBounds(213, 137, 127, 25);
		frame.getContentPane().add(letter);
	}
	
	private void CreateButton(JRadioButton book, JRadioButton article, JRadioButton report, JRadioButton letter) {
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(book.isSelected()) {
					latexEditor.setType("bookTemplate");
				}
				else if(report.isSelected()) {
					latexEditor.setType("reportTemplate");
				}
				else if(article.isSelected()) {
					latexEditor.setType("articleTemplate");
				}
				else if(letter.isSelected()) {
					latexEditor.setType("letterTemplate");
				}
				else {
					latexEditor.setType("emptyTemplate");
				}

				latexEditor.getController().enact("create");
				new MainWindow(latexEditor);
				frame.dispose();
			}
		});
		btnCreate.setBounds(213, 196, 97, 25);
		frame.getContentPane().add(btnCreate);
	}
	
	private void BackButton() {
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(previous.equals("main")) {
					new MainWindow(latexEditor);
					frame.dispose();
				}
				else {
					new OpeningWindow();
					frame.dispose();
				}
			}
		});
		btnBack.setBounds(46, 196, 97, 25);
		frame.getContentPane().add(btnBack);
	}
	
}
