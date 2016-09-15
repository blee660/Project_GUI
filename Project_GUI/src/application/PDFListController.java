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

public class PDFListController{
	
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
	
	@FXML
	public void removePDF(){
		List<MyListItem> selected = listView.getSelectionModel().getSelectedItems();
		
		for(MyListItem mli : selected){
			Library.getInstance().removePDF(mli.pdf);
			listView.getItems().remove(mli);
		}
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
		mli.setPrefWidth(listView.getMaxWidth());
		currentItems.add(mli);
		listView.getItems().add(mli);
		pdf.setListItem(mli);
	}
}
