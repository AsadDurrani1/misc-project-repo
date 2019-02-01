package calculatorview;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CalculateButton extends Button implements EventHandler<ActionEvent>{
	private MatrixCalculator matrixCalculator;
	
	public CalculateButton(MatrixCalculator matrixCalculator) {
		super("Calculate");
		this.matrixCalculator = matrixCalculator;
		setPrefWidth(matrixCalculator.getScene().getWidth()/4);
		setPrefHeight(matrixCalculator.getScene().getHeight());
		setFont(new Font("Agency FB Bold", matrixCalculator.getScene().getWidth()*0.04));
		setAlignment(Pos.CENTER);
		ButtonConfig.setEventHandlers(this);
		matrixCalculator.getScene().widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setPrefWidth(newValue.intValue()/4);
				setFont(new Font("Agency FB Bold", newValue.intValue()*0.04));
			}
		});
		
		matrixCalculator.getScene().heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setPrefHeight(newValue.intValue());
			}
		});
		setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		matrixCalculator.getMatrixBuilderPanel().updateMatrix();
		matrixCalculator.beginResultsScreen();
	}
}
