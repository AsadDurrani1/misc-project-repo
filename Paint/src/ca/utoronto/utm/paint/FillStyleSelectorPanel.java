package ca.utoronto.utm.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** This class implements a GUI panel to be used by a View object that also has a PaintPanel as an attribute. The user can 
 * toggle the fill-style of subsequent Shapes to be drawn on the PaintPanel.
 *
 */
public class FillStyleSelectorPanel extends GridPane implements EventHandler<ActionEvent> {
	
	private View view;
	
	public FillStyleSelectorPanel(View view) {
		this.view = view;
		ToggleButton fillButton = new ToggleButton();
		ImageView iv = new ImageView(new Image("Fill.png"));
		iv.setFitHeight(15);
		iv.setFitWidth(15);
		fillButton.setGraphic(iv);
		fillButton.setPrefWidth(165);
		fillButton.setPrefHeight(40);
		this.add(fillButton, 0, 0);
		fillButton.setOnAction(this);
	}
	
	@Override
	public void handle(ActionEvent event) {
		this.view.getPaintPanel().toggleFillStyle();
	}

}
