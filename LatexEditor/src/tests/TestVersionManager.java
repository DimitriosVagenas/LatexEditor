package tests;

import model.VersionsManager;
import model.Document;
import model.strategies.VersionsStrategy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import controller.LatexEditor;

class TestVersionManager {

	@Test
	void testEnable() {
		VersionsStrategy strategy = null;
		LatexEditor latexEditor = null;
		VersionsManager versionsManager = new VersionsManager(strategy,latexEditor);
		versionsManager.enable();
		assertEquals(versionsManager.isEnabled(),true);	
	}
	
	@Test
	void testDisable() {
		VersionsStrategy strategy = null;
		LatexEditor latexEditor = null;
		VersionsManager versionsManager = new VersionsManager(strategy,latexEditor);
		versionsManager.disable();
		assertEquals(versionsManager.isEnabled(),false);	
	}
	
	@Test
	void testSetCurrentVersion() {
		Document document = new Document("Bibo","1/1/1","copyratia","1.0","blabla");
		VersionsStrategy strategy = null;
		LatexEditor latexEditor = new LatexEditor();
		VersionsManager versionsManager = new VersionsManager(strategy,latexEditor);
		versionsManager.setCurrentVersion(document);
		LatexEditor latexEditorofversionManager =versionsManager.getEditor();
		assertEquals(latexEditorofversionManager.getCurrentDocument().getContents(),"blabla");	
		assertEquals(latexEditorofversionManager.getCurrentDocument().getVersionID(),"1.0");
	}
	
}
