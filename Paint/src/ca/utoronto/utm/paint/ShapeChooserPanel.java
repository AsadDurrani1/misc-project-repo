package ca.utoronto.utm.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is used to initialize the buttons for each drawable shape with the correct label. 
 * After creating the buttons for each individual shape, this class also works to call the correct 
 * ShapeManipulatorStrategy when desired shape is changed by user in order for the correct shapes to be
 * drawn on the canvas.
 * 
 */

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

	private View view;
	private StrategyAbstract strategy;
	
	/**
	 * Initializes a panel and ToggleButtons that displays the ToggleButtons with the labels of all the shapes 
	 * in the buttonLabels array as well as a corresponding sets the graphic on each button to the corresponding
	 * shape as the label.
	 * 
	 * @param view specific View Object to interact with 
	 */
	public ShapeChooserPanel(View view) {
		this.view = view;

		String[] buttonLabels = { "Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Eraser"};
		
		ToggleGroup toggleGroup = new ToggleGroup();
		
		int row = 0;
		for (String label : buttonLabels) {
			ToggleButton button = new ToggleButton(label);
			button.setMinWidth(100);
			button.setMinHeight(51.5);
			
			//deals with graphic portion of the ToggleButtons
			button.setMinHeight(53);
			ImageView iv = new ImageView(new Image(label + ".png"));
			iv.setFitHeight(15);
			button.setGraphic(iv);
			button.setContentDisplay(ContentDisplay.TOP);
		
			this.add(button, 0, row);
			button.setToggleGroup(toggleGroup);
			row++;
			button.setOnAction(this);
		}
	}
	
	/**
	 * Takes care of all the user inputs regarding the change of drawing shape. Updates the 
	 * strategy as well as the mode in paint panel in order for the appropriate shape to be drawn.
	 */
	@Override
	public void handle(ActionEvent event) {
		String command = ((ToggleButton) event.getSource()).getText();
		this.view.getPaintPanel().setMode(command);
		StrategyAbstract oldStrategy = this.strategy;
		this.strategy = StrategyFactory.getStrategy(command, this.view.getPaintPanel());
		if (oldStrategy != null) {
			oldStrategy.deregister();
		}
		this.view.getPaintPanel().repaint();
		System.out.println(command);	
	}
}