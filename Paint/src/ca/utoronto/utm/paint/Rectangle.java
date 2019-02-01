package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is a child of the Shape abstract class contains methods and variables relating only specifically to Rectangle
 * objects. Specific methods this class has include, the constructor, setters and getters of length, width, and origin of the Rectangle,
 * as well as drawFinal() method.
 */
public class Rectangle extends Shape implements DrawingCommand {
	
	private int length;
	private int width;
	
	/**
	 * Constructor to Rectangle in which color, fill, and thickness are not specified.
	 * @param origin top left corner coordinates of Rectangle
	 * @param length length of rectangle
	 * @param width width of rectangle
	 */
	public Rectangle(Point origin, int length, int width) {
		super(origin);
		this.length = length;
		this.width = width;
	}
	
	/**
	 * Constructor to Rectangle in which color, fill, and thickness are specified.
	 * 
	 * @param origin top left corner coordinates of Rectangle
	 * @param length length of rectangle
	 * @param width width of rectangle
	 * @param c specified color of rectangle
	 * @param filled whether the rectangle is filled
	 * @param thickness thickness of rectangle sides
	 */
	public Rectangle(Point origin, int length, int width, Color c, boolean filled, double thickness) {
		super(origin, c, filled, thickness);
		this.length = length;
		this.width = width;
	}
	
	public void setOriginX(int x) {
		this.origin.setX(x);
	}
	
	public void setOriginY(int y) {
		this.origin.setY(y);
	}
	
	public int getLength() {
		return this.length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Draw this Rectangle object onto some GraphicsContext input, with the proper attributes of 
	 * a Rectangle. Satisfies the execute() part of the DrawingCommand
	 */
	public void drawFinal(GraphicsContext g) {
		int x = this.getOrigin().getX();
		int y = this.getOrigin().getY();
		int length = this.getLength();
		int width = this.getWidth();
		
		g.setLineWidth(this.getThickness());
		if (this.getFilled() == false) {
			g.setStroke(this.getColour());
			g.strokeRect(x, y, width, length);
		} else {
			g.setFill(this.getColour());
			g.fillRect(x, y, width, length);
		}
	}
	
	public String toString() {
		return "Rectangle :" + this.origin + " length=" + this.length + " width=" + this.width;
	}
	
	public String getType() {
		return "Rectangle";
	}
}
