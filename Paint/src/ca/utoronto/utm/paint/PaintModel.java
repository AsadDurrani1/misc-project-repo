package ca.utoronto.utm.paint;
import java.util.ArrayList;
import java.util.Observable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** This class represents a current state in a PaintPanel. Concrete Shapes with DrawingCommands are added to a stack. a PaintPanel
 *  observer asks this object to execute all of its commands via the drawAll() method every time a Shape is added.
 *  
 */
public class PaintModel extends Observable {
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> redo = new ArrayList<Shape>();
	private int erased = 0;
	
	/** Called by all Shape Strategies. Adds a new Shape w/ DrawingCommand onto the shapes stack. Notifies observer 
	 * (in this case PaintPanel) that a Shape has been added. PaintPanel then calls repaint() which calls drawAll(), 
	 * resulting in the Shapes of the stack being drawn onto the PaintPanel's canvas in the order they were added to the stack.
	 * 
	 * @param Shape s
	 */
	public void addShape(Shape s) {
		this.shapes.add(s);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void addErased() {
		this.erased++;
	}
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public int getCounterAmount() {
		return shapes.size() - this.erased;
	}
	
	public int getSize() {
		return shapes.size();
	}
	
	public String getTypeOfLastShape() {
		return shapes.get(shapes.size()-1).getType();
	}
	
	/** 
	 * Clear both stacks, called by the menu in View to clear the canvas in PaintPanel so that the next time drawAll() is called,
	 * there are no shapes to draw, also makes it so that all Shapes in the redo stack are forgotten.
	 */
	public void clearArray() {
		this.shapes.clear();
		this.redo.clear();
		this.setChanged();
		this.notifyObservers();
		}
	
	/** 
	 * Removes the most recently added Shape from the shape stack and stores it in the redo stack. This allows the user to add this
	 * shape back to the stack via Menu -> Redo -> PaintModel.redo() which moves this shape back onto the Undo stack. Also notifies
	 * an observing PaintPanel that a change has been made so that the next time drawAll() is called, this most recent Shape
	 * is no longer in the stack.
	 */
	public void undo() {
		if (this.shapes.size() == 0) {
			return;
		}
		this.redo.add(this.shapes.remove(this.shapes.size() - 1));
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Restores the most recently undone Shape from the redo stack and stores it back in the Shapes stack. Also notifies
	 * an observing PaintPanel that a change has been made so that the next time drawAll() is called, this most recent Shape
	 * is back in the Stack.
	 */
	public void redo() {
		if (this.redo.size() == 0) {
			return;
		}
		this.shapes.add(this.redo.remove(this.redo.size() - 1));
		this.setChanged();
		this.notifyObservers();
	}
	
	/** 
	 * Asks every shape to execute its drawFinal() command onto a GraphicsContext. Called
	 * by the repaint() method of an observing PaintPanel().
	 * 
	 * @param GraphicsContext g
	 */
	public void drawAll(GraphicsContext g) {
		for (Shape s: this.shapes) {
			s.drawFinal(g);
		}
	}
	
	/**
	 * Displays the change counter i on the canvas. 
	 * @param GraphicsContext g
	 */
	public void iCounter(GraphicsContext g) {
		g.setStroke(Color.BLACK);
		g.setLineWidth(1);
		int i = this.getCounterAmount();
		g.strokeText("i=" + i, 10, 10);
	}
}