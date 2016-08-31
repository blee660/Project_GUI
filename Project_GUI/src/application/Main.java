package application;
	
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import workerThreads.TemplateThread;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static File homeDir;
	
	private static MainController mc;
	
	@Override
	public void start(Stage primaryStage) {
		
		
		String fileName = System.getProperty("user.home") + File.separator + "GUI700Home";
		int i = 1;
		
		homeDir = new File(fileName);
		while(homeDir.exists()){
			homeDir = new File(fileName + "(" + i + ")");
			i++;
		}
		
		homeDir.mkdir();
		
		
		try {
//			BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("/application/ProjectGUI.fxml"));
			Scene scene = new Scene(root,900,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void registerMC(MainController mc1){
		mc = mc1;
	}
	
	public static MainController getMC(){
		return mc;
	}
}
