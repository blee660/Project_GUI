package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pdfClasses.Library;
import pdfClasses.PDF;

public class MyListItem extends HBox {

	Label title = new Label();
	Label location = new Label();
	Pane pane = new Pane();
	Button button = new Button("");
	VBox box = new VBox();

	ContextMenu cm = new ContextMenu();

	PDF pdf;

	public MyListItem(String titleText, PDF pdf) {
		super();

		title.setTextOverrun(OverrunStyle.ELLIPSIS);

		this.pdf = pdf;
		this.title.setText(titleText);
		this.location.setText(pdf.getFileLocation());
		this.location.setFont(new Font(8));
		this.location.setTextFill(Color.DARKGRAY);

		this.box.getChildren().setAll(title, location);

		this.getChildren().setAll(box, pane, button);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setMinWidth(USE_PREF_SIZE);

		this.setHgrow(pane, Priority.ALWAYS);

		setup();
	}

	public void updateLabel(String text) {
		Platform.runLater(() -> {
			title.setText(text);
		});

	}

	private void setup() {
		setupContext();
		button.setId("menu-button");
		button.getStylesheets().add("application.css");
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg) {
				if (arg.getButton() == MouseButton.PRIMARY) {
					cm.show(button, arg.getScreenX(), arg.getScreenY());
				}
			}
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					Main.getMC().setCurrent(pdf);
				}
			}
		});
	}

	private void setupContext() {
		MenuItem item1 = new MenuItem("View as HTML");
		MenuItem item2 = new MenuItem("View as PDF");
		MenuItem item3 = new MenuItem("View Bibliography");
		cm.getItems().addAll(item1, new SeparatorMenuItem(), item2, new SeparatorMenuItem(), item3);

		item1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!Desktop.isDesktopSupported()) {
					showDialog("Sorry, this is not supported on this computer");
					return;
				}
				if (pdf.getHTMLFile() == null) {
					showDialog("Sorry, HTML file is not ready yet! \nPlease wait a few seconds and try again \n");
					return;
				}
				try {
					Desktop.getDesktop().browse(pdf.getHTMLFile().toURI());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		item2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!Desktop.isDesktopSupported()) {
					showDialog("Sorry, this is not supported on this computer");
					return;
				}
				
				try {
					Desktop.getDesktop().open(new File(pdf.getFileLocation()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		item3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!Desktop.isDesktopSupported()) {
					showDialog("Sorry, this is not supported on this computer");
					return;
				}
				if (pdf.getHTMLFile() == null) {
					showDialog("Sorry, HTML file is not ready yet! \nPlease wait a few seconds and try again \n");
					return;
				}
				try {
					Desktop.getDesktop().browse(pdf.getHTMLFile().toURI());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
	}

	private void showDialog(String text) {
		Button closeButton = new Button("OK");

		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(new Scene(VBoxBuilder.create().children(new Text(text), closeButton)
				.alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.show();
		

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				dialogStage.close();		
			}
		});
	}
	
}