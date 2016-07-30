package application;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;

public class PDFListController {

	private FileChooser fc = new FileChooser();

	public PDFListController() {
		setFilters();
	}

	public void addPDF(ActionEvent event) {
		
		List<File> selectedFiles = fc.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());

		if (selectedFiles != null) {
			for (File f : selectedFiles) {
				System.out.println(f.getAbsolutePath());
			}
		}
	}

	public void removePDF(ActionEvent event) {
		System.out.println("removed");
	}

	private void setFilters() {
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", ".pdf");
		fc.getExtensionFilters().add(extFilter);
		fc.setSelectedExtensionFilter(extFilter);
	}

}
