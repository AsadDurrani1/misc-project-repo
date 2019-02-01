package ca.utoronto.utm.paint;

import java.util.Observable;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * The StrategyPolyline object is a child of Observable and it implements the StrategyAbstract interface and the EventHandler
 * for MouseEvents. When called, this class creates an instance of Polyline with the chosen attributes by listening to MouseEvents and 
 * puts the created instance at the top of the PaintModel command stack.
 * 
 * StrategyPolyline is also known as PolylineManipulatorStrategy.
 */
public class StrategyPolyline extends Observable implements StrategyAbstract, EventHandler<MouseEvent> {
	public Polyline polyline; 
	private PaintPanel paintPanel;
	private Point lastPoint;
	private boolean lastCompleted;
	private static boolean modeChanged;
	
	@Override
	/**
	 * This method creates and returns an instance of the current StrategyPolyline.
	 * 
	 * @param paintPanel 	The PaintPanel is passed to the strategy in order to set the current selected attributes
	 * 						for the Polyline we want to create.
	 */
	public StrategyAbstract newStrategy(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
		this.lastCompleted = true;
		if (this.paintPanel.getModel().getSize() == 0 || this.paintPanel.getModel().getTypeOfLastShape() != "Polyline") {
			StrategyPolyline.modeChanged = true;
		} 
		this.paintPanel.addEventHandler(MouseEvent.ANY, this);
		return this;
	}

	@Override
	/**
	 * This method calls the appropriate method when a certain MouseEvent occurs in order to create a Polyline object.
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED && this.paintPanel.getMode() == "Polyline") {
			mouseClicked(event);
		} 
	}

	/**
	 * This method creates a Polyline that continues from the last if the last Polyline was not ended and if the mode has not changed.
	 * Otherwise, it creates a new Polyline given the coordinates of the MouseEvent.
	 * If the Polyline has been created already, it sets the second point given the coordinates of the MouseEvent.
	 * In all cases, it uses current colour and thickness from PaintPanel. 
	 * 
	 * @param event		The MouseEvent that has occurred.
	 */
	private void mouseClicked(MouseEvent event) { 
		if (!(this.lastCompleted) && !(StrategyPolyline.modeChanged)) {
			this.polyline = new Polyline(this.lastPoint.getX(), this.lastPoint.getY(), this.paintPanel.getColour(), this.paintPanel.getThickness());
			this.polyline.setSecondPoint((int) event.getX(), (int) event.getY(), this.paintPanel.getColour(), this.paintPanel.getThickness());

		} else {
			this.polyline = new Polyline((int) event.getX(), (int) event.getY(), this.paintPanel.getColour(), this.paintPanel.getThickness());
		}

		if (this.polyline.noSecondPoint()) {
			this.polyline.setSecondPoint((int) event.getX(), (int) event.getY(), this.paintPanel.getColour(), this.paintPanel.getThickness());
		}	

		if (this.polyline != null && !this.polyline.noSecondPoint()) {
			GraphicsContext gc = this.paintPanel.getCanvas().getGraphicsContext2D();
			Point p1 = this.polyline.getOrigin();
			Point p2 = this.polyline.getSecondPoint();
			gc.setStroke(p2.getColour());
			gc.setLineWidth(p2.getThickness());
			gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
		
		if (event.getClickCount() == 2) {
			setLastNull();
			this.lastCompleted = true;
			createShape();
			
		} else {
			int x2 = this.polyline.getSecondX();
			int y2 = this.polyline.getSecondY();
			this.lastPoint = new Point (x2, y2, this.polyline.second.getColour(), this.polyline.second.getThickness());
			this.lastCompleted = false;
			StrategyPolyline.modeChanged = false;
			createShape();
		}
	}
	
	/**
	 * This method gets rid of the current value of lastPoint.
	 */
	public void setLastNull() {
		this.lastPoint = null;
	}
	
	/**
	 * This method sets the value of modeChanged to true when the mode is not that of Polyline.
	 */
	public static void modeIsChanged() {
		StrategyPolyline.modeChanged = true;
	}

	@Override
	/**
	 * This method adds the created instance of the Polyline to the top of the PaintModel command stack.
	 * Required by the StrategyAbstract interface.
	 */
	public void createShape() {
		if (this.paintPanel.getMode() == "Polyline") {
			this.paintPanel.getModel().addShape(this.polyline);
		} 
		this.paintPanel.repaint();
		this.polyline = null;
		return;	
	}

	@Override
	/**
	 * This method removes the handler for MouseEvents to prevent unnecessary Polyline objects from being created.
	 * Required by the StrategyAbstract interface.
	 */
	public void deregister() {
		this.paintPanel.removeEventHandler(MouseEvent.ANY, this);
	}
}
