package ca.utoronto.utm.paint;

import java.util.Observable;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * The StrategySquare object is a child of Observable and it implements the StrategyAbstract interface and the EventHandler
 * for MouseEvents. When called, this class creates an instance of Square with the chosen attributes by listening to MouseEvents and 
 * puts the created instance at the top of the PaintModel command stack.
 * 
 * StrategySquare is also known as SquareeManipulatorStrategy.
 */
public class StrategySquare extends Observable implements StrategyAbstract, EventHandler<MouseEvent> {
	public Square square;
	private PaintPanel paintPanel;
	
	@Override
	/**
	 * This method creates and returns an instance of the current StrategySquare.
	 * 
	 * @param paintPanel 	The PaintPanel is passed to the strategy in order to set the current selected attributes
	 * 						for the Square we want to create.
	 */
	public StrategyAbstract newStrategy(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		this.paintPanel.addEventHandler(MouseEvent.ANY, this);
		return this;
	}

	@Override
	/**
	 * This method calls the appropriate method when a certain MouseEvent occurs in order to create a Square object.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mousePressed(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && this.paintPanel.getMode() == "Square") {
			mouseDragged(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseReleased(event);
		} 
	}

	/**
	 * This method creates a Square object given the coordinates of the MouseEvent and the current colour, fill-style and thickness
	 * from PaintPanel. 
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mousePressed(MouseEvent event) {
		Point origin = new Point((int)event.getX(), (int)event.getY());
		int sideLength = 0;
		this.square = new Square(origin, sideLength, this.paintPanel.getColour(), this.paintPanel.getFilled(), this.paintPanel.getThickness());
	}

	/**
	 * This method updates the side-length of the Square object that is being created given the coordinates of the MouseEvent.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseReleased(MouseEvent event) {
		Point originalPoint = new Point(this.square.getOrigin().getX(), this.square.getOrigin().getY());
		int length = Math.abs((int) event.getY() - (int) this.square.getOrigin().getY());
		int width = Math.abs((int) event.getX() - (int) this.square.getOrigin().getX());
		this.square.setSideLength(Math.max(length,width));
		int sidelength = this.square.getSideLength();
			
		if(event.getX() > this.square.getOrigin().getX() && event.getY() < this.square.getOrigin().getY()) {
			this.square.setOriginY(originalPoint.getY() - sidelength);
		}else if(event.getX() < this.square.getOrigin().getX() && event.getY() > this.square.getOrigin().getY()) {
			this.square.setOriginX(originalPoint.getX() - sidelength);	
		}else if(event.getX() < this.square.getOrigin().getX() && event.getY() < this.square.getOrigin().getY()) {
			this.square.setOrigin(new Point(originalPoint.getX() - sidelength,originalPoint.getY() - sidelength));
		}
		createShape();
	}

	/**
	 * This method provides the feedback of the current Square object that is being created.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseDragged(MouseEvent event) {
		int originalPointX = this.square.getOrigin().getX();
		int originalPointY = this.square.getOrigin().getY();
		int length = Math.abs((int) event.getY() - originalPointY);
		int width = Math.abs((int) event.getX() - originalPointX);
		int sidelength = Math.max(width, length);
		
		if(event.getX() > originalPointX && event.getY() < originalPointY) {
			originalPointY = originalPointY - sidelength;
		}else if(event.getX() < originalPointX && event.getY() > originalPointY) {
			originalPointX = originalPointX - sidelength;
		}else if(event.getX() < originalPointX && event.getY() < originalPointY) {
			originalPointX = originalPointX - sidelength;
			originalPointY = originalPointY - sidelength;
		}
		
		GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
		this.paintPanel.repaint();
			
		gc.setLineWidth(this.square.getThickness());
		if (this.square.filled == false) { 
			gc.setStroke(this.square.getColour());
			gc.strokeRect(originalPointX, originalPointY, sidelength, sidelength);
		} else {
			gc.setFill(this.square.getColour());
			gc.fillRect(originalPointX, originalPointY, sidelength, sidelength);
		}	
	}

	@Override
	/**
	 * This method adds the created instance of the Square to the top of the PaintModel command stack.
	 * Required by the StrategyAbstract interface.
	 */
	public void createShape() {
		if (this.paintPanel.getMode() == "Square") {
			this.paintPanel.getModel().addShape(this.square);
		}
		this.paintPanel.repaint();
		this.square = null;
		return;
	}

	@Override
	/**
	 * This method removes the handler for MouseEvents to prevent unnecessary Square objects from being created.
	 * Required by the StrategyAbstract interface.
	 */
	public void deregister() {
		this.paintPanel.removeEventHandler(MouseEvent.ANY, this);
	}
}
