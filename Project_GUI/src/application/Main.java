package application;
	
import java.io.File;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * Main class for launching GUI application
 * Creates a directory, per user session, to store the output files of each tool execution 
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class Main extends Application {
	
	public static File sessionDir;
	public static File parentDir;
	
	private static MainController mc;
	
	@Override
	public void start(Stage primaryStage) {
		
		// Parent directory
		String fileName = System.getProperty("user.home") + File.separator + "GUI700Home";
		
		// Session directory
		String timeStamp = new SimpleDateFormat("dd.MMM.yyyy_hh.mm").format(new java.util.Date());
		
		// Create directories
		parentDir = new File(fileName);
		sessionDir = new File(fileName + "/" + timeStamp);

		if(!parentDir.exists()){
			parentDir.mkdir();
		}
		sessionDir.mkdir();		
		
		// Primary stage to set application scene
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("/application/ProjectGUI.fxml"));
			Scene scene = new Scene(root,900,700);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// launch application
		launch(args);
	}

	public static void registerMC(MainController mc1){
		mc = mc1;
	}

	public static MainController getMC(){
		return mc;
	}
}
