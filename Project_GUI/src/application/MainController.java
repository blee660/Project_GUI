package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pdfClasses.PDF;

/**
 * 
 * Main Controller class that 
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class MainController implements Initializable {

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
	Text abstractx;

	@FXML
	VBox wrapper;
	
	@FXML
	ScrollPane sp;

	public MainController() {
		Main.registerMC(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startBindings();
	}

	public void setCurrent(PDF pdf) {
		if (pdf.getMetadata().getTitle() != null) {
			setLabels(title, pdf.getMetadata().getTitle());
		} else {
			setLabels(title, "Unknown");
		}

		setLabels(path, pdf.getFileLocation());

		if (pdf.getMetadata().getAuthors() != null) {
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for (String x : pdf.getMetadata().getAuthors()) {
				if (count != 0) {
					sb.append(", ");
				}
				sb.append(x);
				count++;
			}

			setLabels(authors, sb.toString());
		}

		if (pdf.getMetadata().getDegree() != null) {
			setLabels(degree, pdf.getMetadata().getDegree());
		} else {
			setLabels(degree, "Unknown");
		}

		if (pdf.getMetadata().getSupervisors() != null) {
			setLabels(supervisors, pdf.getMetadata().getSupervisors());
		} else {
			setLabels(supervisors, "Unknown");
		}

		if (!pdf.getKeyWords().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for (String x : pdf.getKeyWords()) {
				if (count != 0) {
					sb.append(", ");
				}
				sb.append(x);
				count++;
			}

			setLabels(keywords, sb.toString());
		} else {
			setLabels(keywords, "None");
		}

		String absContent = "";
		if (pdf.getMetadata().getAbstractx() != null) {
			absContent = pdf.getMetadata().getAbstractx();
		}

		if (!absContent.equals("")) {
			abstractx.setText(beautifyAbs(absContent));
		} else {
			abstractx.setText("Unknown");
		}
	}

	private void setLabels(Label l, String t) {
		if (t.equals("")) {
			t = "Unknown";
		}
		l.setText(t);
	}

	private void startBindings() {
		sp.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				resize();
			}
		});
	}
	
	private void resize(){
		double newWidth = sp.getWidth();
		double paddings = wrapper.getPadding().getLeft() + wrapper.getPadding().getRight();
		
		System.out.println(newWidth);
		wrapper.setPrefWidth(sp.getWidth() - 30);
		
		title.setPrefWidth(newWidth - title.getLayoutX() - paddings);
		path.setPrefWidth(newWidth - path.getLayoutX() - paddings);
		
		double pref = newWidth - authors.getLayoutX() - paddings;

		authors.setPrefWidth(pref);
		keywords.setPrefWidth(pref);
		abstractx.setWrappingWidth(pref - 35);
		degree.setPrefWidth(pref);
		supervisors.setPrefWidth(pref);
	}
	
	private String beautifyAbs(String input){
		StringBuilder sb = new StringBuilder();
		
		String[] split = input.split("\r?\n");
		int avLength = 0;
		for(String x: split){
			avLength += x.length();
		}
		
		avLength =avLength/split.length;
		
		boolean first = true;
		for(String x: split){
			if(first){
				sb.append(x + " ");
				first = false;
				continue;
			}
			
			if(x.length() - 5 < avLength && x.matches("(.)+(\\.|\\?|\\!)")){
				sb.append( x + "\n");
			}
			else{
				sb.append(x + " ");
			}
		}
		
		return sb.toString().replaceAll("  ", " ");
	}
}
