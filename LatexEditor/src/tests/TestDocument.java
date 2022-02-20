package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Document;



class TestDocument {
	
	@Test
	void testGetContents() {
		Document document = new Document("Bibo","1/1/1","copyratia","1.0","blabla");
		assertEquals(document.getContents(),"blabla");
	}

	@Test
	void testSetContents() {
		Document document = new Document("Bibo","1/1/1","copyratia","1.0","blabla");
		document.setContents("BlimBlom");
		assertEquals(document.getContents(),"BlimBlom");
	}

	@Test
	void testChangeVersion() {
		Document document = new Document("Bibo","1/1/1","copyratia","2","blabla");
		document.changeVersion();
		assertNotEquals(document.getVersionID(),"2");
		assertEquals(document.getVersionID(),"3");
	}
}
