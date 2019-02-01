package ca.utoronto.utm.paint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Polyline object is a child of the Shape abstract class and it implements the DrawingCommand interface. It has an origin, 
 * colour and thickness, and is an ArrayList containing 2 points. When called, this class creates a Polyline object with all required 
 * attributes.
 * 
 */
public class Polyline extends Shape implements DrawingCommand{
	int originX, originY, secondX, secondY;
	Point second;
	ArrayList<Point> polyline;
	
	/**
	 * Constructor for a Polyline object. 
	 * 
	 * @param x			The x-coordinate of the origin of the Polyline. 
	 * @param y			The y-coordinate of the origin of the Polyline.
	 * @param colour	The colour of the Polyline.
	 * @param thickness	The thickness of the Polyline.
	 */
	public Polyline(int x, int y, Color colour, double thickness) {
		super(new Point(x,y), colour, false, thickness);
		this.polyline = new ArrayList<Point>();
		this.polyline.add(this.origin);
	}
	
	public void addPoint(Point point) {
		this.polyline.add(point);
	}
	
	public Point getPoint(int i) {
		return this.polyline.get(i);
	}
	
	public int getSize() {
		return this.polyline.size();
	}
	
	public int getOriginX() {
		return this.originX;
	}
	
	public void setOriginX(int x) {
		this.originX = x;
	}
	
	public int getOriginY() {
		return this.originY;
	}
	
	public void setOriginY(int y) {
		this.originY = y;
	}
	
	public Point getSecondPoint() {
		return this.second;
	}
	
	public void setSecondPoint(int x2, int y2) {
		this.secondX = x2;
		this.secondY = y2;
		this.second = new Point(this.secondX, this.secondY);
		addPoint(this.second);
	}
	
	public int getSecondX() {
		return this.secondX;
	}
	
	public int getSecondY() {
		return this.secondY;
	}
	
	public void setSecondPoint(int x2, int y2, Color c, double t) {
		this.secondX = x2;
		this.secondY = y2;
		this.second = new Point(this.secondX, this.secondY, c, t);
		addPoint(this.second);
	}
	
	/**
	 * This method returns true if the second Point has not yet been set.
	 * 
	 * @return boolean 	True if the second Point is null.
	 */
	public boolean noSecondPoint() {
		return this.second == null;
	}
	
	/**
	 * This method defines how to draw a Polyline object onto some GraphicsContext with respect to its attributes. 
	 * It is the execute() aspect of the DrawingCommand interface.
	 */
	public void drawFinal(GraphicsContext g) {
		if (this.second != null) {
			Point p1 = this.getOrigin();
			Point p2 = this.getSecondPoint();
			g.setStroke(this.second.getColour());
			g.setLineWidth(this.second.getThickness());
			g.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
	}
	
	public String toString() {
		return "Polyline :"+ this.origin + " #points=" + this.polyline.size();
	}
	
	public String getType() {
		return "Polyline";
	}
}
