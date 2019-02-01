package ca.utoronto.utm.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

/**
 * This class defines a GUI panel to be added to the window by View which allows the user to choose the line thickness
 * of the Shape they would like to create. A Slider is used to choose the desired line thickness.
 *
 */
public class LineThicknessPanel extends GridPane implements EventHandler<ActionEvent> {

	private View view;
	private Label thicknessLabel;
	
	/**
	 * The Slider is implemented in order for the user to choose a line thickness between 0 and 10.
	 * 
	 * @param view	The View is passed to the Panel in order for the Gridpane to be added to the View.
	 */
	public LineThicknessPanel(View view) {
		
		this.view = view;
		this.thicknessLabel = new Label("Thickness: 1");
		this.setAlignment(Pos.CENTER);

		Slider chooseThickness = new Slider(0, 10.0, 2.0);	
		chooseThickness.setBlockIncrement(2);
		chooseThickness.setMajorTickUnit(2);
		chooseThickness.setShowTickLabels(true);
		chooseThickness.setShowTickMarks(true);
		chooseThickness.setSnapToTicks(true);
		chooseThickness.setOrientation(Orientation.HORIZONTAL);
		chooseThickness.setValue(1);
		
		/**
		 * This is the Slider Listener which listens for a change in the thickness in order for the 
		 * correct thickness to be displayed and implemented by the desired Shape.
		 * 
		 */
		chooseThickness.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {				
				changeThickness(newValue.intValue());
				setLabel(newValue.intValue());
			}
		});
		
		this.add(chooseThickness, 0, 0);
		this.add(this.thicknessLabel, 0, 1);	
	}
	
	protected void setLabel(double newThickness) {
		this.thicknessLabel.setText("Thickness: " + Double.toString(newThickness));
	}

	/**
	 * This method updates the current line thickness in PaintPanel.
	 * @param newThickness
	 */
	public void changeThickness(double newThickness) {
		this.view.getPaintPanel().setCurrentThickness(newThickness + 1);
	}
	
	@Override
	public void handle(ActionEvent event) {}

}
