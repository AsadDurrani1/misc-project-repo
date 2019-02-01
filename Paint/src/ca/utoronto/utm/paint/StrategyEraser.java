package ca.utoronto.utm.paint;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * The StrategyEraser object is a child of Observable and it implements the StrategyAbstract interface and the EventHandler
 * for MouseEvents. When called, this class creates an instance of Eraser by listening to MouseEvents and 
 * puts the created instance at the top of the PaintModel command stack.
 * 
 */
public class StrategyEraser implements StrategyAbstract, EventHandler<MouseEvent>{
	private Eraser eraser;
	private PaintPanel paintPanel;
	
	/**
	 * This method creates and returns an instance of the current StrategyEraser.
	 * 
	 * @param paintPanel 	The PaintPanel is passed to the strategy in order to access the current Graphics Context.
	 */
	public StrategyAbstract newStrategy(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		this.paintPanel.addEventHandler(MouseEvent.ANY, this);
		return this;
	}
	
	/**
	 * This method calls the appropriate method when a certain MouseEvent occurs in order to create an Eraser object.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mousePressed(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			mouseDragged(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseReleased(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
			mouseMoved(event);
		}
	}
	
	/**
	 * This method creates an Eraser object given the coordinates of the MouseEvent.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void mousePressed(MouseEvent event) {
		this.eraser = new Eraser((int)event.getX(), (int)event.getY());
	}
	
	/**
	 * This method determines when the current Eraser is complete and allows the Eraser to be added to the command stack.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseReleased(MouseEvent event) {
		this.createShape();
	}


	/**
	 * This method allows the user to see where the Eraser is on the canvas while the Eraser mode is selected.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseMoved(MouseEvent event) {
		GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
		this.paintPanel.repaint();
		gc.setLineWidth(1);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(event.getX() - 10, event.getY() - 10, 20, 20);
	}
	
	/**
	 * This method adds Points to the current Eraser object given the coordinates of the MouseEvent.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseDragged(MouseEvent event) {
		this.eraser.addPoint(new Point((int)event.getX(), (int)event.getY(), Color.WHITE, this.eraser.getThickness()));
		GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
		this.paintPanel.repaint();
		for (int i = 0; i < this.eraser.getSize()-1; i++) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(this.eraser.getThickness());
			gc.strokeLine(this.eraser.getPoint(i).getX(), this.eraser.getPoint(i).getY(), 
					this.eraser.getPoint(i+1).getX(), this.eraser.getPoint(i+1).getY());
		}
		gc.setLineWidth(1);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(event.getX() - 10, event.getY() - 10, this.eraser.getThickness(), this.eraser.getThickness());
	}

	@Override
	/**
	 * This method adds the created instance of the Eraser to the top of the PaintModel command stack.
	 * Required by the StrategyAbstract interface.
	 */
	public void createShape() {
		if (this.paintPanel.getMode() != "Eraser") {
			return;
		}
		this.paintPanel.getModel().addErased();
		this.paintPanel.getModel().addShape(this.eraser);
	}

	@Override
	/**
	 * This method removes the handler for MouseEvents to prevent unnecessary Eraser objects from being created.
	 * Required by the StrategyAbstract interface.
	 */
	public void deregister() {
		this.paintPanel.removeEventHandler(MouseEvent.ANY, this);
	}
}
