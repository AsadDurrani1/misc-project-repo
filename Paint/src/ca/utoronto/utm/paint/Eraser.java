package ca.utoronto.utm.paint;

/**
 * This class implements an Eraser object to remove what is currently drawn on the canvas.
 */
public class Eraser extends Squiggle {
	public Eraser(int x, int y) {
		super(x, y);
		this.thickness = 20;
	}
}
