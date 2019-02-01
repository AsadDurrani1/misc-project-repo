package ca.utoronto.utm.paint;

import java.util.Observable;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * The StrategyCircle object is a child of Observable and it implements the StrategyAbstract interface and the EventHandler
 * for MouseEvents. When called, this class creates an instance of Circle with the chosen attributes by listening to MouseEvents and 
 * puts the created instance at the top of the PaintModel command stack.
 * 
 * StrategyCircle is also known as CircleManipulatorStrategy.
 */
public class StrategyCircle extends Observable implements StrategyAbstract, EventHandler<MouseEvent> {
	private Circle circle;
	private PaintPanel paintPanel;
	
	@Override
	/**
	 * This method creates and returns an instance of the current StrategyCircle.
	 * 
	 * @param paintPanel 	The PaintPanel is passed to the strategy in order to set the current selected attributes
	 * 						for the Circle we want to create.
	 */
	public StrategyAbstract newStrategy(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		this.paintPanel.addEventHandler(MouseEvent.ANY, this);
		return this;
	}

	@Override
	/**
	 * This method calls the appropriate method when a certain MouseEvent occurs in order to create a Circle object.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
		mousePressed(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && this.paintPanel.getMode() == "Circle") {
			mouseDragged(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseReleased(event);
		}
	}
	
	/**
	 * This method creates a Circle object given the coordinates of the MouseEvent and the current colour, fill-style and thickness
	 * from PaintPanel. 
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mousePressed(MouseEvent event) {
		Point centre = new Point((int) event.getX(), (int) event.getY());
		int radius = 0;
		this.circle = new Circle(centre, radius, this.paintPanel.getColour(), this.paintPanel.getFilled(), this.paintPanel.getThickness());
	}

	/**
	 * This method updates the radius of the Circle object that is being created given the coordinates of the MouseEvent.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseReleased(MouseEvent event) {
		int xValues = (int) event.getX() - (int) this.circle.getOrigin().getX();
		int yValues = (int) event.getY() - (int) this.circle.getOrigin().getY();
		double xDifference = Math.pow(xValues, 2);
		double yDifference = Math.pow(yValues, 2);	
		double radius = Math.sqrt(xDifference + yDifference);	
		this.circle.setRadius((int) radius);
		createShape();
	}

	/**
	 * This method provides the feedback of the current Circle object that is being created.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseDragged(MouseEvent event) {
		GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
		
		int xTemp = this.circle.getOrigin().getX();
		int yTemp = this.circle.getOrigin().getY();
		
		if (this.circle != null) {
			int xTempValues = (int) event.getX() - xTemp;
			int yTempValues = (int) event.getY() - yTemp;
			double xTempDiff = Math.pow(xTempValues, 2);
			double yTempDiff = Math.pow(yTempValues, 2);
			int radiusTemp = (int) Math.sqrt(xTempDiff + yTempDiff);
			int diameterTemp = 2*radiusTemp;
			
			this.paintPanel.repaint();

			gc.setLineWidth(this.circle.getThickness());
			if (this.circle.filled == false) { 
				gc.setStroke(this.circle.getColour()); 
				gc.strokeOval(xTemp-radiusTemp, yTemp-radiusTemp, diameterTemp, diameterTemp);
			} else {
				gc.setFill(this.circle.getColour());
				gc.fillOval(xTemp-radiusTemp, yTemp-radiusTemp, diameterTemp, diameterTemp);
			}
		}
	}
	
	@Override
	/**
	 * This method adds the created instance of the Circle to the top of the PaintModel command stack.
	 * Required by the StrategyAbstract interface.
	 */
	public void createShape() {
		if (this.paintPanel.getMode() == "Circle") {
			this.paintPanel.getModel().addShape(this.circle);
		}
		this.paintPanel.repaint();
		this.circle = null;
		return;
	}

	@Override
	/**
	 * This method removes the handler for MouseEvents to prevent unnecessary Circle objects from being created.
	 * Required by the StrategyAbstract interface.
	 */
	public void deregister() {
		this.paintPanel.removeEventHandler(MouseEvent.ANY, this);
	}
}
