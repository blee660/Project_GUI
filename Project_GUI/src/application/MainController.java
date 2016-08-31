package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import pdfClasses.PDF;

public class MainController implements Initializable{
	
	
	@FXML
	Label title;

	@FXML
	Label path;
	
	@FXML
	Label authors;
	
	@FXML
	Label degree;
	
	@FXML
	Label supervisors;
	
	@FXML
	Label keywords;
	
	@FXML
	Label abstractx;
	
	@FXML
	HBox abstractBox;
	
	public MainController(){
		Main.registerMC(this);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void setCurrent(PDF pdf){
		if(pdf.getMetadata().getTitle()!=null){
			setLabels(title, pdf.getMetadata().getTitle());
		}
		else{
			setLabels(title, "Unknown");
		}
		
		setLabels(path, pdf.getFileLocation());
		
		if(pdf.getMetadata().getAuthors() != null){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for(String x: pdf.getMetadata().getAuthors()){
				if (count != 0){
					sb.append(", ");
					count++;
				}
				sb.append(x);
			}
			
			setLabels(authors, sb.toString());
		}
		
		if(pdf.getMetadata().getDegree()!= null){
			setLabels(degree, pdf.getMetadata().getDegree());
		}
		else{
			setLabels(degree, "Unknown");
		}
		
		if(pdf.getMetadata().getSupervisors()!= null){
			setLabels(supervisors, pdf.getMetadata().getSupervisors());
		}
		else{
			setLabels(supervisors, "Unknown");
		}
		
		if(!pdf.getKeyWords().isEmpty()){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for(String x: pdf.getKeyWords()){
				if(count!=0){
					sb.append(", ");
					count++;
				}
				sb.append(x);
			}
			
			setLabels(keywords, sb.toString());
		}
		else{
			setLabels(keywords, "None");
		}
		
		if(pdf.getMetadata().getAbstractx()!= null){
			setLabels(abstractx, pdf.getMetadata().getAbstractx());
		}
		else{
			setLabels(abstractx, "Unkown");
		}
	}
	
	private void setLabels(Label l, String t){
		if(t.equals("")){
			t = "Unknown";
		}
		l.setText(t);
	}
}
