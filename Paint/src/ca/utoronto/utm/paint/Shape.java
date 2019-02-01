package ca.utoronto.utm.paint;

/** Structure for an abstract Shape object; parent class for Rectangle, Circle, Polyline, etc. Each shape is initialized with
 * an origin coordinate, a colour, a line thickness, and a fill style. Each inheriting class has the bare minimum information
 * to draw itself onto a GraphicsContext via a DrawingCommand, which is also implemented in inheriting classes.
 * 
 */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
	protected Point origin;
	protected Color colour;
	protected boolean filled;
	protected double thickness;
	
	/** Default Constructor: Initializes a shape with a specified origin and placeholder colour, fill-style and thickness,
	 * 
	 * @param origin: Point that object is to be drawn relative to
	 */
	public Shape(Point origin) {
		this.origin = origin;
		this.colour = Color.WHITE;
		this.filled = false;
		this.thickness = 1;
	}
	
	/** Initializes a shape with a specified origin, colour, fill-style and thickness.
	 * 
	 * @param Point origin: Point that object is to be drawn relative to
	 * @param Color colour: Colour of object
	 * @param boolean filled: Whether or not the object is filled in
	 * @param double thickness: Line thickness of object
	 */
	public Shape(Point origin, Color colour, boolean filled,  double thickness) {
		this.origin = origin;
		this.colour = colour;
		this.filled = filled;
		this.thickness = thickness;
	}
	
	public Point getOrigin() {
		return this.origin;
	}
	
	public void setOrigin(Point p) {
		this.origin = p;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public void setColour(Color c) {
		this.colour = c;
	}
	
	public boolean getFilled() {
		return filled;
	}
	
	public void setFilled(boolean b) {
		this.filled = b;
	}

	public double getThickness() {
		return this.thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public void drawFinal(GraphicsContext g) {}
	
	public abstract String getType();
}
