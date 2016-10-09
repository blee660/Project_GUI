package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pdfClasses.Library;
import pdfClasses.PDF;

/**
 * 
 * Controller class for the list of documents
 * allows adding and removal of PDF documents from the library
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class PDFListController {
	
	private FileChooser fc = new FileChooser();

	@FXML
	TextField search = new TextField();
	
	@FXML 
	Button searchButton = new Button();
	
	@FXML
	ListView<MyListItem> listView = new ListView<MyListItem>();
	List<MyListItem> currentItems = new ArrayList<MyListItem>();
	
	public PDFListController() {
		// set filters to only show PDF documents
		setFilters();
		Library.getInstance().registerDisplay(this);
	}

	@FXML
	public void search(){
		
	}
	
	
	//=============================================================================================================================
	public void addPDF(ActionEvent event) {
		
		// allow users to choose files to add into library
		List<File> selectedFiles = fc.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());

		// add selected documents into library
		if (selectedFiles != null) {
			Library.getInstance().addPDFFiles(selectedFiles);
		}
		
		// print paths of all documents
		Library.getInstance().printAllPaths();
	}

	//=============================================================================================================================
	
	@FXML
	public void removePDF(){
		// get selected items from the library
		List<MyListItem> selected = listView.getSelectionModel().getSelectedItems();
		
		// remove selected items
		for(MyListItem mli : selected){
			Library.getInstance().removePDF(mli.pdf);
			listView.getItems().remove(mli);
		}
	}

	//=============================================================================================================================
	private void setFilters() {
		
		// only allow selection of PDF documents
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		fc.getExtensionFilters().add(extFilter);
		fc.setSelectedExtensionFilter(extFilter);
	}
	
	//=============================================================================================================================
	//ListView Methods
	
	// adding item into document list
	public void addItem(PDF pdf){
		String title = "Unknown";
		// set title field as extracted title
		if(pdf.getMetadata().getTitle() != null && !pdf.getMetadata().getTitle().equals("")){
			title = pdf.getMetadata().getTitle();
		}
		
		// create new list item with document
		MyListItem mli = new MyListItem(title, pdf);
		mli.setPrefWidth(listView.getMaxWidth());
		// add item into current item list 
		currentItems.add(mli);
		// display item in list
		listView.getItems().add(mli);
		pdf.setListItem(mli);
	}

}
