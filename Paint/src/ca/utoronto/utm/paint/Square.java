package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Square child of Shape class and DrawingCommand. Each square has a side-length. Unlike Rectangle, a Square has no
 * attribute containing its width and height, as they are always equal. For this reason, Square inherits directly from
 * Shape.
 * 
 */
public class Square extends Shape implements DrawingCommand{
	private int sideLength;
	
	/** 
	 * Default constructor of a Square object. 
	 * Initializes a Square with specified side-length and default colour, fill-style and thickness.
	 * 
	 * @param origin		The point that the Square should be drawn relative to.
	 * @param sideLength	The side-length of the Square.
	 */
	public Square(Point origin, int sideLength) {
		super(origin);
		this.sideLength = sideLength;
	}
	
	/**
	 * Constructor of a Square object. 
	 * 
	 * @param origin		The point that the Square should be drawn relative to.
	 * @param sideLength	The side-length of the Square.
	 * @param colour		The colour of the Square.
	 * @param filled		Whether the Square is filled or not.
	 * @param thickness		The thickness of the Square's outline.
	 */
	public Square(Point origin, int sideLength, Color colour, boolean filled, double thickness) {
		super(origin, colour, filled, thickness);
		this.sideLength = sideLength;
	}
	
	public void setOriginX(int x) {
		this.origin.setX(x);
	}
	
	public void setOriginY(int y) {
		this.origin.setY(y);
	}
	
	public int getSideLength() {
		return this.sideLength;
	}
	
	public void setSideLength(int length) {
		this.sideLength = length;
	}
	
	/**
	 * This method defines how to draw a Square object onto some GraphicsContext with respect to its attributes. 
	 * It is the execute() aspect of the DrawingCommand interface.
	 */
	public void drawFinal(GraphicsContext g) {
		int x = this.getOrigin().getX();
		int y = this.getOrigin().getY();
		int sideLength = this.sideLength;
		g.setLineWidth(this.thickness);
		if(!this.getFilled()) {
			g.setStroke(this.colour);
			g.strokeRect(x, y, sideLength, sideLength);
		}else {
			g.setFill(this.colour);
			g.fillRect(x, y, sideLength, sideLength);
		}
	}
	
	public String toString() {
		return "Square :" + this.origin + " sidelength=" + this.sideLength;
	}
	
	public String getType() {
		return "Square";
	}
}
