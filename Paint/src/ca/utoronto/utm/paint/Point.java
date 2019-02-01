package ca.utoronto.utm.paint;

import javafx.scene.paint.Color;

/** 
 * A Point consists of two coordinates.
 * Provides a basis for which all Shape objects are constructed on.
 *
 */
public class Point {
	int x, y;
	private Color colour;
	private double thickness;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, Color c, double thickness) {
		this(x, y);
		this.colour = c;
		this.thickness = thickness;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Color getColour() {
		return this.colour;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}
	
	public double getThickness() {
		return this.thickness;
	}
	
	public String toString() {
		return "("+this.x+","+this.y+")";
	}
}
