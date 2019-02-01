package ca.utoronto.utm.paint;

/**
 * This factory class is called upon by the ShapeChooserPanel in order to 
 * generate a shape specific strategy concrete subclass, while allowing the subclasses 
 * of the shape specific strategies to decide what gets instantiated. 
 * 
 */
public class StrategyFactory {
	
	/**
	 * getStrategy method used to get the ShapeManipulatorStrategy for a specific
	 * shape
	 * @param label shape for which strategy is required
	 * @param paintPanel
	 * @return generate concrete subclass of wanted Shape.
	 */
	public static StrategyAbstract getStrategy(String label, PaintPanel paintPanel) {
		if(label.equals("Rectangle")) {
			StrategyPolyline.modeIsChanged();
			return new StrategyRectangle().newStrategy(paintPanel);
		}if (label.equals("Square")) {
			StrategyPolyline.modeIsChanged();
			return new StrategySquare().newStrategy(paintPanel);
		}if (label.equals("Circle")) {
			StrategyPolyline.modeIsChanged();
			return new StrategyCircle().newStrategy(paintPanel);
		}if (label.equals("Squiggle")) {
			StrategyPolyline.modeIsChanged();
			return new StrategySquiggle().newStrategy(paintPanel);
		}if (label.equals("Eraser")) {
			return new StrategyEraser().newStrategy(paintPanel);
		}if (label.equals("Polyline")) {
			return new StrategyPolyline().newStrategy(paintPanel);
		} else return null;
	}
}