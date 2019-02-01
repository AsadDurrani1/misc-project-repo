package ca.utoronto.utm.paint;

import java.util.Observable;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * The StrategyRectangle object is a child of Observable and it implements the StrategyAbstract interface and the EventHandler
 * for MouseEvents. When called, this class creates an instance of Rectangle with the chosen attributes by listening to MouseEvents and 
 * puts the created instance at the top of the PaintModel command stack.
 * 
 * StrategyRectangle is also known as RectangleManipulatorStrategy.
 */
public class StrategyRectangle extends Observable implements StrategyAbstract, EventHandler<MouseEvent> {
	public Rectangle rectangle;
	private PaintPanel paintPanel;
	
	@Override
	/**
	 * This method creates and returns an instance of the current StrategyRectangle.
	 * 
	 * @param paintPanel 	The PaintPanel is passed to the strategy in order to set the current selected attributes
	 * 						for the Rectangle we want to create.
	 */
	public StrategyAbstract newStrategy(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		this.paintPanel.addEventHandler(MouseEvent.ANY, this);
		return this;
	}

	@Override
	/**
	 * This method calls the appropriate method when a certain MouseEvent occurs in order to create a Rectangle object.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mousePressed(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && this.paintPanel.getMode() == "Rectangle") {
			mouseDragged(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseReleased(event);
		} 
	}

	/**
	 * This method creates a Rectangle object given the coordinates of the MouseEvent and the current colour, fill-style and thickness
	 * from PaintPanel. 
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mousePressed(MouseEvent event) {
		Point origin = new Point((int) event.getX(), (int) event.getY());
		int length = 0;
		int width = 0;
		this.rectangle = new Rectangle(origin, length, width, this.paintPanel.getColour(), this.paintPanel.getFilled(), this.paintPanel.getThickness());	
	}
	
	/**
	 * This method updates the length and width of the Rectangle object that is being created given the coordinates of the MouseEvent.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseReleased(MouseEvent event) {
		Point originalPoint = new Point(this.rectangle.getOrigin().getX(), this.rectangle.getOrigin().getY());
		int length = Math.abs((int) event.getY() - (int) this.rectangle.getOrigin().getY());
		int width = Math.abs((int) event.getX() - (int) this.rectangle.getOrigin().getX());
		this.rectangle.setLength(length);
		this.rectangle.setWidth(width);
		
		if(event.getX() > this.rectangle.getOrigin().getX() && event.getY() < this.rectangle.getOrigin().getY()) {
			this.rectangle.setOriginY(originalPoint.getY() - length);
		}else if(event.getX() < this.rectangle.getOrigin().getX() && event.getY() > this.rectangle.getOrigin().getY()) {
			this.rectangle.setOriginX(originalPoint.getX() - width);	
		}else if(event.getX() < this.rectangle.getOrigin().getX() && event.getY() < this.rectangle.getOrigin().getY()) {
			this.rectangle.setOrigin(new Point(originalPoint.getX() - width, originalPoint.getY() - length));
		}
		createShape();
	}

	/**
	 * This method provides the feedback of the current Rectangle object that is being created.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseDragged(MouseEvent event) {
		int originalPointX = this.rectangle.getOrigin().getX();
		int originalPointY = this.rectangle.getOrigin().getY();
		int length = Math.abs((int) event.getY() - originalPointY);
		int width = Math.abs((int) event.getX() - originalPointX);
		
		if(event.getX() > originalPointX && event.getY() < originalPointY) {
			originalPointY = originalPointY - length;
		}else if(event.getX() < originalPointX && event.getY() > originalPointY) {
			originalPointX = originalPointX - width;
		}else if(event.getX() < originalPointX && event.getY() < originalPointY) {
			originalPointX = originalPointX - width;
			originalPointY = originalPointY - length;
		}
			
		GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
		this.paintPanel.repaint();
			
		gc.setLineWidth(this.rectangle.getThickness());
		if (this.rectangle.filled == false) { 
			gc.setStroke(this.rectangle.getColour());
			gc.strokeRect(originalPointX, originalPointY, width, length);
		} else {
			gc.setFill(this.rectangle.getColour());
			gc.fillRect(originalPointX, originalPointY, width, length);
		}	
	}

	@Override
	/**
	 * This method adds the created instance of the Rectangle to the top of the PaintModel command stack.
	 * Required by the StrategyAbstract interface.
	 */
	public void createShape() {
		if (this.paintPanel.getMode() == "Rectangle") {
			this.paintPanel.getModel().addShape(this.rectangle);
		}
		this.paintPanel.repaint();
		this.rectangle = null;
		return;
	}

	@Override
	/**
	 * This method removes the handler for MouseEvents to prevent unnecessary Rectangle objects from being created.
	 * Required by the StrategyAbstract interface.
	 */
	public void deregister() {
		this.paintPanel.removeEventHandler(MouseEvent.ANY, this);
	}
}
