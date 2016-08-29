package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import pdfClasses.Library;
import pdfClasses.PDF;
import workerThreads.*;

public class PDFListController {
	
	private FileChooser fc = new FileChooser();

	@FXML
	ListView<MyListItem> listView = new ListView<MyListItem>();
	List<MyListItem> currentItems = new ArrayList<MyListItem>();
	
	public PDFListController() {
		setFilters();
		Library.getInstance().registerDisplay(this);
	}

	//=============================================================================================================================
	public void addPDF(ActionEvent event) {
		
		List<File> selectedFiles = fc.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());

		if (selectedFiles != null) {
			Library.getInstance().addPDFFiles(selectedFiles);
		}
		
		Library.getInstance().printAllPaths();
	}

	//=============================================================================================================================
	public void removePDF(ActionEvent event) {
		System.out.println("removed");
	}

	//=============================================================================================================================
	private void setFilters() {
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		fc.getExtensionFilters().add(extFilter);
		fc.setSelectedExtensionFilter(extFilter);
	}
	
	//=============================================================================================================================
	//ListView Methods
	
	public void addItem(PDF pdf){
		String title = "Unknown";
		if(pdf.getMetadata().getTitle() != null && !pdf.getMetadata().getTitle().equals("")){
			title = pdf.getMetadata().getTitle();
		}
		MyListItem mli = new MyListItem(title, pdf);
		currentItems.add(mli);
		listView.getItems().add(mli);
		pdf.setListItem(mli);
	}
}
