package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 
 * Main class for launching GUI application Creates a directory, per user
 * session, to store the output files of each tool execution
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
		String fileName = System.getProperty("user.home") + File.separator
				+ "GUI700Home";

		// Session directory
		String timeStamp = new SimpleDateFormat("dd.MMM.yyyy_hh.mm")
				.format(new java.util.Date());

		// Create directories
		parentDir = new File(fileName);
		sessionDir = new File(fileName + "/" + timeStamp);

		if (!parentDir.exists()) {
			parentDir.mkdir();
		}
		sessionDir.mkdir();

		// Primary stage to set application scene
		try {
			Parent root = FXMLLoader.load(Main.class
					.getResource("/application/ProjectGUI.fxml"));
			Scene scene = new Scene(root, 900, 700);
			scene.getStylesheets().add(
					Main.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// launch application
		prepResources();
		launch(args);
	}

	public static void registerMC(MainController mc1) {
		mc = mc1;
	}

	public static MainController getMC() {
		return mc;
	}

	public static void prepResources() {
		ArrayList<String> resourceName = new ArrayList<String>();
		resourceName.add("PDFList.fxml");
		resourceName.add("ClassificationTab.fxml");
		resourceName.add("MetadataTab.fxml");
		resourceName.add("ProjectGUI.fxml");
		resourceName.add("application.css");

		ArrayList<String> images = new ArrayList<String>();
		images.add("add.png");
		images.add("menu.png");
		images.add("remove.png");
		images.add("search2.png");

		try {
			for (String x : resourceName) {
				File fxml = new File(x);
				if (fxml.exists()) {
					continue;
				}

				fxml.createNewFile();

				InputStream is = Main.class.getResourceAsStream("/application/"
						+ x);
				OutputStream os = new FileOutputStream(fxml);

				int read = 0;

				byte[] bytes = new byte[1024];

				System.out.println(x);
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}

				os.close();
				is.close();

				fxml.deleteOnExit();

			}
			for(String x: images){
				File fxml = new File(x);
				if (fxml.exists()){
					continue;
				}
				
				fxml.createNewFile();
				
				InputStream is = Main.class.getResourceAsStream("/resources/"+x);
				OutputStream os = new FileOutputStream(fxml);

				int read = 0;

				byte[] bytes = new byte[1024];

				System.out.println(x);
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}

				os.close();
				is.close();
				
				fxml.deleteOnExit();
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
