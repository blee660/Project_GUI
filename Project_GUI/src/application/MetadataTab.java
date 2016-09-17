package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import pdfClasses.PDF;

/**
 * 
 * Class that updates the main tab window for 
 * displaying the extracted metadata information.
 * 
 * */
public class MetadataTab extends Tab{

	private PDF pdf;
	
	private String x = "Unknown";
	
	// Metadata fields
	private Label titleContent = new Label(x);
	private Label locationContent = new Label(x);
	private Label authorContent = new Label(x);
	private Label abstractxContent = new Label(x);
	
	public MetadataTab(PDF pdf){
		super();
		this.pdf = pdf;
	}
	
	// update metadata fields
	public void updateFields(){
		// update title
		updateLabel(titleContent,pdf.getMetadata().getTitle());
		// update file path
		updateLabel(locationContent, pdf.getFileLocation());
		
		// update author(s)
		StringBuilder authors = new StringBuilder();
		int current = 0;
		// separate multiple authors with a comma
		for(String x: pdf.getMetadata().getAuthors()){
			if(current!=0){
				authors.append(", ");
			}
			authors.append(x);
		}
		updateLabel(authorContent, authors.toString());
		
		// update abstract content
		updateLabel(abstractxContent, pdf.getMetadata().getAbstractx());
	}
	
	// method for updating labels for each field
	private void updateLabel(Label label, String content){
		if(!content.equals("") || content == null){
			return;
		}
		
		Platform.runLater(() -> {
            label.setText(content);
        });
	}
}
