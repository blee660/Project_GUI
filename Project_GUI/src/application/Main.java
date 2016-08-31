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
	
	private ArrayList<TemplateThread> workerList = new ArrayList<TemplateThread>();
	
	@Override
	public void start(Stage primaryStage) {
		
		registerWorkers();
		initialWorkerThreadStart();
		
		
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
	
	public void registerWorkers(){
		
	}
	
	public void initialWorkerThreadStart(){
		for(TemplateThread tt : workerList){
			tt.start();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
