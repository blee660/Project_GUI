package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import pdfClasses.PDF;

public class MyListItem extends HBox{
	
	Label label= new Label();
	Pane pane = new Pane();
	Button button = new Button(">");
	
	ContextMenu cm = new ContextMenu();
	
	PDF pdf;
	
	public MyListItem(String labelText, PDF pdf){
		super();

		this.pdf = pdf;
		this.label.setText(labelText);
		
		this.getChildren().setAll(label, pane, button);
		MyListItem.setHgrow(pane, Priority.ALWAYS);
		setup();
	}
	
	public void updateLabel(String text){
		label.setText(text);
	}
	
	private void setup(){
		MenuItem item1 = new MenuItem("View as HTML");
		MenuItem item2 = new MenuItem( "View as PDF");
		MenuItem item3 = new MenuItem( "Show metadata");
		cm.getItems().addAll(item1, item2, new SeparatorMenuItem(), item3);
		
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg) {
				if(arg.getButton() == MouseButton.PRIMARY){
					cm.show(button, arg.getScreenX(),arg.getScreenY());
				}
			}
		});
	}
}