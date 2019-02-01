package ca.utoronto.utm.paint;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.Observable;
import java.util.Observer;

/**
 * This class extends StackPane holds the most recently updated settings of the attributes as well 
 * as the settings for the Paint application, and updated view of the Canvas.
 *
 */
class PaintPanel extends StackPane implements Observer, EventHandler<MouseEvent> {
	private PaintModel model;
	private View view;
	private Color currentColour = Color.BLACK;
	private boolean filled = false;
	private double lineThickness = 1;
	private String mode;
	private Canvas canvas;

	public PaintPanel(PaintModel model, View view) {

		this.canvas = new Canvas(300, 450);
		this.getChildren().add(this.canvas);
		this.setStyle("-fx-background-color: white");
		this.addEventHandler(MouseEvent.ANY, this);
		this.mode = "Circle";
		this.model = model;
		this.model.addObserver(this);
		this.view = view;
		
	}

	public void repaint() {
		GraphicsContext g = this.canvas.getGraphicsContext2D();

		g.clearRect(0, 0, this.getWidth(), this.getHeight()); 
		g.setStroke(this.currentColour);
		this.model.drawAll(g);
		this.model.iCounter(g);
	}
	
	public void setCanvasWidth(double width) {
		this.canvas.setWidth(width);
		repaint();
	}
	
	public void setCanvasHeight(double height) {
		this.canvas.setHeight(height);
		repaint();
	}
	
	public Color getColour() {
		return this.currentColour;
	}
	
	public boolean getFilled() {
		return this.filled;
	}
	
	public double getThickness() {
		return this.lineThickness;
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public String getMode() {
		return this.mode;
	}
	
	public PaintModel getModel() {
		return this.model;
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}

	/**
	 * Controller aspect of this
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setCurrentColour(Color c) {
		this.currentColour = c;
	}
	
	public void toggleFillStyle() {
		this.filled = (this.filled) ? false : true;
	}

	public void setCurrentThickness(double lt) {
		this.lineThickness = lt;
	}
	
	@Override
	public void handle(MouseEvent event) {}
}
