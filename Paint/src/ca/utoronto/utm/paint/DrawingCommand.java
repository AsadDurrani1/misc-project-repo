package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;

/** Provides an interface for Shape objects to implement so that Shapes can draw themselves after they have been initialized. Each implementing
 * class should implement drawFinal() to be able to draw itself onto a GraphicsContext, and to be invoked by some intermediate 
 * object (in this case, PaintPanel and PaintModel).
 * 
 */
public interface DrawingCommand {
	void drawFinal(GraphicsContext g);
}
