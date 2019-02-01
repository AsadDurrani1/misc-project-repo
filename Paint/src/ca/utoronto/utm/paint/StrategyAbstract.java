package ca.utoronto.utm.paint;

/**
 * This interface provides an outline for an abstract ShapeManipulatorStrategy
 * to be built upon and further specified as required depending on the specific 
 * requirements of a certain ShapeManipulatorStrategy. 
 * 
 */

public interface StrategyAbstract {
	public StrategyAbstract newStrategy(PaintPanel paintPanel);
	public void createShape();
	public void deregister();
}