package application;
	
import java.io.File;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static File sessionDir;
	public static File parentDir;
	
	private static MainController mc;
	
	@Override
	public void start(Stage primaryStage) {
		
		
		String fileName = System.getProperty("user.home") + File.separator + "GUI700Home";
		String timeStamp = new SimpleDateFormat("dd.MMM.yyyy_hh.mm").format(new java.util.Date());
		
		parentDir = new File(fileName);
		sessionDir = new File(fileName + "/" + timeStamp);

		if(!parentDir.exists()){
			parentDir.mkdir();
		}
		sessionDir.mkdir();		
		
		try {
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
