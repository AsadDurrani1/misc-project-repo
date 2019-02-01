package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Circle object is a child of the Shape abstract class and it implements the DrawingCommand interface. It has an origin, 
 * colour, fill and thickness, as well as a radius. When called, this class creates a Circle object with all required attributes.
 * 
 */
public class Circle extends Shape implements DrawingCommand {
	private int radius;
	
	/**
	 * Constructor of a Circle object. 
	 * 
	 * @param origin	The center point of the Circle.
	 * @param radius	The radius of the Circle.
	 * @param colour	The colour of the Circle.
	 * @param filled	Whether the Circle is filled or not.
	 * @param thickness	The thickness of the Circle's outline.
	 */
	public Circle(Point origin, int radius, Color colour, boolean filled, double thickness) {
		super(origin, colour, filled, thickness);
		this.radius=radius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	@Override 
	/**
	 * This method defines how to draw a Circle object onto some GraphicsContext with respect to its attributes. 
	 * It is the execute() aspect of the DrawingCommand interface.
	 */
	public void drawFinal(GraphicsContext g) {
		int x = this.getOrigin().getX();
		int y = this.getOrigin().getY();
		int radius = this.getRadius();
		int diameter = 2*radius;
		
		g.setLineWidth(this.getThickness());
		if (this.getFilled() == false) {
			g.setStroke(this.getColour());
			g.strokeOval(x-radius, y-radius, diameter, diameter);
		} else {
			g.setFill(this.getColour());
			g.fillOval(x-radius, y-radius, diameter, diameter);
		}
	}
	
	public String toString() {
		return "Circle :" + this.origin + " radius=" + this.radius;
	}
	
	public String getType() {
		return "Circle";
	}
}
