package ca.utoronto.utm.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class ColourChooserPanel extends BorderPane implements EventHandler<ActionEvent> {
	
	private View view;
	private Canvas currentColourView;
	
	/** Initializes a panel that displays 21 colours to the user along with a current colour display. Consists
	 * of a BorderPane, which contains the colour grid and a canvas. This class is to be used as an attribute for a 
	 * View object that has a PaintPanel attribute. When a user clicks a button, the View's PaintPanel is updated to 
	 * draw all following shapes in that colour, until the colour is changed again.
	 * 
	 * @param view a View object
	 */
	public ColourChooserPanel(View view) {
		
		this.view = view;
		GridPane g = new GridPane();
		Color[] colours = {Color.BLACK, Color.DARKGREY, Color.GREY, Color.MAROON, Color.DARKRED, Color.BROWN, 
				Color.RED, Color.CORAL, Color.ORANGE, Color.GOLD, Color.YELLOW, Color.GREEN, Color.YELLOWGREEN, Color.CHARTREUSE, 
				Color.DARKBLUE, Color.BLUE, Color.AQUAMARINE, Color.TURQUOISE, Color.SKYBLUE, Color.PURPLE, Color.INDIGO, 
				Color.BLUEVIOLET, Color.DEEPPINK, Color.PINK, Color.BEIGE, Color.AZURE, Color.WHITE};
		int row = 0;
		int column = 0;
		
		for (Color colour : colours) {
			Button b = new Button();
			b.setStyle("-fx-background-color: #" + getColourString(colour) + "; " + "-fx-border-width: 2;" + "-fx-border-color: black;");
			g.add(b, column, row);
			if (column >= 2) {
				column = 0;
				row++;
			} else {
				column++;
			}
			b.setOnAction(this);
		}
		this.setTop(g);
		this.currentColourView = new Canvas(60, 56);
		GraphicsContext gc = this.currentColourView.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 60, 65);
		this.setBottom(this.currentColourView);
	}
	
	/**
	 * This method returns a CSS-style string representation of a Color of the form #ffxxxx 
	 * It is called by the constructor method.
	 **/
	private static String getColourString(Color colour) {
		return colour.toString().substring(2, colour.toString().length()-2);
	}
	
	@Override
	/**Updates the paint panel's current colour according to a button pressed on this panel. Also updates this.currentColourView
	 * to display the selected colour (default = null)
	 * **/ 
	public void handle(ActionEvent event) {
		Color c = (Color)((Button)(event.getSource())).getBackground().getFills().get(0).getFill();
		this.view.getPaintPanel().setCurrentColour(c);
		GraphicsContext gc = this.currentColourView.getGraphicsContext2D();
		gc.setFill(c);
		gc.fillRect(2, 2, 56, 52);
	}
}
