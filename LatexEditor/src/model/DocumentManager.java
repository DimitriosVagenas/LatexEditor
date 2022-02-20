package model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


public class DocumentManager {
	 private HashMap<String, Document> templates;
	 static private DocumentManager singleDocumentManager;
	
	 private DocumentManager() {
		templates = new HashMap<String, Document>();
		setTemplate("reportTemplate");
		setTemplate("bookTemplate");
		setTemplate("articleTemplate");
		setTemplate("letterTemplate");
		setTemplate("emptyTemplate");
	}
	
	private void setTemplate(String template) {
		Document document = new Document();
		if (template != "emptyTemplate"){
			document.setContents(getTemplate(template));
		}
		templates.put(template, document);
	}
	
	public static DocumentManager getDocumentManager() {
		if (singleDocumentManager == null) {
			singleDocumentManager = new DocumentManager();
        }
        return singleDocumentManager;
	}
	
	public Document createDocument(String type) {
		return templates.get(type).clone();
	}
	
	public String getTemplate(String type){
		String content ="";
		try {
			content = Files.readString(Paths.get("templates\\" + type + ".txt"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return content;
	}
}