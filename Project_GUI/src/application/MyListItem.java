package application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pdfClasses.PDF;

public class MyListItem extends HBox{
	
	
	Label title= new Label();
	Label location = new Label();
	Pane pane = new Pane();
	Button button = new Button("");
	
	VBox box = new VBox();
	
	ContextMenu cm = new ContextMenu();
	
	
	PDF pdf;
	
	public MyListItem(String titleText, PDF pdf){
		super();

		title.setTextOverrun(OverrunStyle.ELLIPSIS);
		
		this.pdf = pdf;
		this.title.setText(titleText);
		this.location.setText(pdf.getFileLocation());
		this.location.setFont(new Font(8));
		this.location.setTextFill(Color.DARKGRAY);
		
		this.box.getChildren().setAll(title,location);
		
		this.getChildren().setAll(box, pane, button);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setMinWidth(USE_PREF_SIZE);
		
		this.setHgrow(pane, Priority.ALWAYS);
		
		setup();
	}
	
	public void updateLabel(String text){
		 Platform.runLater(() -> {
	            title.setText(text);
	        });

	}
	
	private void setup(){
		MenuItem item1 = new MenuItem("View as HTML");
		MenuItem item2 = new MenuItem("View as PDF");
		MenuItem item4 = new MenuItem("Remove from library");
		cm.getItems().addAll(item1, item2, new SeparatorMenuItem(), item4);
		
		button.setId("menu-button");
		button.getStylesheets().add("application.css");
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
		
			@Override
			public void handle(MouseEvent arg) {
				if(arg.getButton() == MouseButton.PRIMARY){
					cm.show(button, arg.getScreenX(),arg.getScreenY());
				}
			}
		});
		
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
					Main.getMC().setCurrent(pdf);
				}
			}
		});
	}
}