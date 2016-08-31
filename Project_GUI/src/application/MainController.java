package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController implements Initializable{

	@FXML
	private TabPane tabPane;
		
	
	public MainController(){
		Main.setMainController(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void addPane(Tab tab){
		Platform.runLater(() -> {
			tabPane.getTabs().add(tab);
        });
	}
}