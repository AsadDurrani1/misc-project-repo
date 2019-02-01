package ca.utoronto.utm.paint;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Squiggle object is a child of the Shape abstract class and it implements the DrawingCommand interface. It has an origin, 
 * colour and thickness, and is an ArrayList containing points. When called, this class creates a Squiggle object with all required 
 * attributes.
 * 
 */
public class Squiggle extends Shape implements DrawingCommand{
	int originX, originY;
	ArrayList<Point> squiggle;

	/**
	 * Default constructor of a Squiggle object. 
	 * Initializes a Squiggle with specified origin and default colour and thickness.
	 * 
	 * @param x		The x-coordinate of the origin of the Squiggle.
	 * @param y		The y-coordinate of the origin of the Squiggle.
	 */
	public Squiggle(int x, int y) {
		super(new Point(x, y));
		this.originX = x;
		this.originY = y;
		this.squiggle = new ArrayList<Point>();
		this.squiggle.add(new Point(x, y));
	}
	
	/**
	 * Construct of a Squiggle object. 
	 * 
	 * @param x			The x-coordinate of the origin of the Squiggle. 
	 * @param y			The y-coordinate of the origin of the Squiggle.
	 * @param colour	The colour of the Squiggle.
	 * @param thickness	The thickness of the Squiggle.
	 */
	public Squiggle(int x, int y, Color colour, double thickness) {
		super(new Point(x,y), colour, false, thickness);
		this.squiggle = new ArrayList<Point>();
		this.squiggle.add(this.origin);
	}

	/**
	 * Adds a Point object to the Squiggle, which is an ArrayList of many points.
	 * 
	 * @param point	The Point object to add to the Squiggle.
	 */
	public void addPoint(Point point) {
		this.squiggle.add(point);
	}
	
	public Point getPoint(int i) {
		return this.squiggle.get(i);
	}
	
	public int getSize() {
		return this.squiggle.size();
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
	
	@Override
	/**
	 * This method defines how to draw a Squiggle object onto some GraphicsContext with respect to its attributes. 
	 * It is the execute() aspect of the DrawingCommand interface.
	 */
	public void drawFinal(GraphicsContext g) {
		for (int i = 0; i < this.getSize() - 1; i++) {
			Point p1 = this.getPoint(i);
			Point p2 = this.getPoint(i + 1);
			g.setStroke(p1.getColour());
			g.setLineWidth(p1.getThickness());
			g.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
	}
	
	public String toString() {
		return "Squiggle :"+ this.origin + " #points=" + this.squiggle.size();
	}
	
	public String getType() {
		return "Squiggle";
	}
}
