package application;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import pdfClasses.PDF;

public class MetadataTab extends Tab{

	private PDF pdf;
	
	private Button viewHTML = new Button();
	private Button viewPDF = new Button();
	private Label title = new Label("Title :");
	private Label location = new Label("Location :");
	private Label authors = new Label("Authors :");
	private Label abstractx = new Label("Abstract :");
	private Label keywords = new Label("Keywords");
	
	private String x = "Unknown";
	
	private Label titleContent = new Label(x);
	private Label locationContent = new Label(x);
	private Label authorContent = new Label(x);
	private Label abstractxContent = new Label(x);
	private ArrayList<Label> keywordContent = new ArrayList<Label>();	
	
	private VBox vbox= new VBox();
	
	public MetadataTab(PDF pdf){
		super();
		this.pdf = pdf;
		
		this.setContent(vbox);
		vbox.getChildren().add(new Label("LOOOOOOOOOOOOOOOOL"));
	}
	
	public void updateFields(){
		updateLabel(titleContent,pdf.getMetadata().getTitle());
		updateLabel(locationContent, pdf.getFileLocation());
		
		StringBuilder authors = new StringBuilder();
		int current = 0;
		for(String x: pdf.getMetadata().getAuthors()){
			if(current!=0){
				authors.append(", ");
			}
			authors.append(x);
		}
		updateLabel(authorContent, authors.toString());
		
		updateLabel(abstractxContent, pdf.getMetadata().getAbstractx());
	}
	
	private void updateLabel(Label label, String content){
		if(!content.equals("") || content == null){
			return;
		}
		
		Platform.runLater(() -> {
            label.setText(content);
        });
	}
}
