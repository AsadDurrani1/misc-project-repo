package ca.utoronto.utm.paint;

import java.util.Observable;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * The StrategySquiggle object is a child of Observable and it implements the StrategyAbstract interface and the EventHandler
 * for MouseEvents. When called, this class creates an instance of Squiggle with the chosen attributes by listening to MouseEvents and 
 * puts the created instance at the top of the PaintModel command stack.
 * 
 * StrategySquiggle is also known as SquiggleManipulatorStrategy.
 */
public class StrategySquiggle extends Observable implements StrategyAbstract, EventHandler<MouseEvent> {
	public Squiggle squiggle;
	protected PaintPanel paintPanel;

	@Override
	/**
	 * This method creates and returns an instance of the current StrategySquiggle.
	 * 
	 * @param paintPanel 	The PaintPanel is passed to the strategy in order to set the current selected attributes
	 * 						for the Squiggle we want to create.
	 */
	public StrategyAbstract newStrategy(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		this.paintPanel.addEventHandler(MouseEvent.ANY, this);
		return this;
	}

	@Override
	/**
	 * This method calls the appropriate method when a certain MouseEvent occurs in order to create a Squiggle object.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mousePressed(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && this.paintPanel.getMode() == "Squiggle") {
			mouseDragged(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseReleased(event);
		}
	}

	/**
	 * This method creates a Squiggle object given the coordinates of the MouseEvent and the current colour and thickness
	 * from PaintPanel. 
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	protected void mousePressed(MouseEvent event) {
		this.squiggle = new Squiggle((int) event.getX(), (int) event.getY(), this.paintPanel.getColour(), this.paintPanel.getThickness());	
	}
	
	/**
	 * This method adds Points to the current Squiggle object given the coordinates of the MouseEvent and the current colour
	 * and thickness from PaintPanel. 
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	protected void mouseDragged(MouseEvent event) {
		this.squiggle.addPoint(new Point((int) event.getX(), (int) event.getY(), this.paintPanel.getColour(), this.paintPanel.getThickness()));
		
		GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
			
		gc.setStroke(this.squiggle.getColour());
		gc.setLineWidth(this.squiggle.getThickness());
			
		for (int i = 0; i < this.squiggle.getSize() - 1; i++) {
			Point p1 = this.squiggle.getPoint(i);
			Point p2 = this.squiggle.getPoint(i + 1);
			gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
	}

	/**
	 * This method determines when the current Squiggle is complete and allows the Squiggle to be added to the command stack.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	protected void mouseReleased(MouseEvent event) {
		createShape();
	}

	@Override
	/**
	 * This method adds the created instance of the Squiggle to the top of the PaintModel command stack.
	 * Required by the StrategyAbstract interface.
	 */
	public void createShape() {
		if (this.paintPanel.getMode() == "Squiggle") {
			this.paintPanel.getModel().addShape(this.squiggle);
		}
		this.squiggle = null;
		return;
	}
	
	@Override
	/**
	 * This method removes the handler for MouseEvents to prevent unnecessary Squiggle objects from being created.
	 * Required by the StrategyAbstract interface.
	 */
	public void deregister() {
		this.paintPanel.removeEventHandler(MouseEvent.ANY, this);
		
	}
}
