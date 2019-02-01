package calculatorview;

import matrixmodel.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class DimensionsPanel extends GridPane implements EventHandler<ActionEvent> {
	private MatrixCalculator matrixCalculator;
	private MatrixBuilderPanel matrixBuilderPanel;
	private String fontFamily;
	protected TextField t1;
	protected TextField t2;
	private Label timesLabel;
	private Button enterButton;
	private int numRows, numCols;
	private static final int maxTextLength = 2;

	public DimensionsPanel(MatrixCalculator matrixCalculator) {
		this.matrixCalculator = matrixCalculator;
		this.matrixBuilderPanel = matrixCalculator.getMatrixBuilderPanel();
		this.t1 = new TextField();
		this.t2 = new TextField();
		this.timesLabel = new Label("x");
		this.enterButton = new Button("Confirm");
		this.fontFamily = matrixCalculator.getFontFamily();
		
		t1.setPrefWidth((int)this.matrixCalculator.getScene().getWidth()/15);
		t2.setPrefWidth((int)this.matrixCalculator.getScene().getWidth()/15);
		t1.setAlignment(Pos.CENTER);
		t2.setAlignment(Pos.CENTER);
		timesLabel.setPrefWidth((int)this.matrixCalculator.getScene().getWidth()/45);
		this.setHgap((int)this.matrixCalculator.getScene().getWidth()/90);
		timesLabel.setFont(new Font(fontFamily, 24));
		enterButton.setFont(new Font("Tacoma", 12));
		enterButton.setPrefWidth(t1.getWidth() + t2.getWidth() + timesLabel.getWidth());
		ButtonConfig.setEventHandlers(enterButton);
		
		t1.setOnKeyPressed(e -> switchFocus(e));
		t2.setOnKeyPressed(e -> switchFocus(e));
		TextFieldConfig.createLengthLimiter(t1, maxTextLength);
		TextFieldConfig.createLengthLimiter(t2, maxTextLength);
		enterButton.setOnAction(this);
		
		this.add(t1, 0, 0);
		this.add(timesLabel, 1, 0);
		this.add(t2, 2, 0);
		this.add(enterButton, 0, 1, 3, 1);
		this.matrixCalculator.getScene().widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				t1.setPrefWidth(newValue.intValue()/20);
				t2.setPrefWidth(newValue.intValue()/20);
				setHgap(newValue.intValue()/60);
				enterButton.setAlignment(Pos.CENTER);
				enterButton.setPrefWidth(t1.getPrefWidth() + t2.getPrefWidth() + timesLabel.getPrefWidth() + 2*getHgap());
				
				enterButton.setFont(new Font(fontFamily, newValue.intValue()*0.03));
				timesLabel.setFont(new Font("Arial Rounded MT Bold", newValue.intValue()*0.04));
				t1.setFont(new Font("Calibri", newValue.intValue()*0.02));
				t2.setFont(new Font("Calibri", newValue.intValue()*0.02));
				setPadding(new Insets(matrixCalculator.getScene().getHeight()/3, 0, 0, newValue.intValue()/2.5));
			}	
		});
		
		this.matrixCalculator.getScene().heightProperty().addListener(new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				t1.setPrefHeight(newValue.intValue()/15);
				t2.setPrefHeight(newValue.intValue()/15);
				setVgap(newValue.intValue()/20);
				enterButton.setPrefHeight(newValue.intValue() / 10);
				setPadding(new Insets(newValue.intValue()/3, 0, 0, matrixCalculator.getScene().getWidth()/2.5));
			}	
		});
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			this.numRows = Integer.parseInt(t1.getText());
			this.numCols = Integer.parseInt(t2.getText());
			if (numRows < 1 || numCols < 1 || numRows > 10 || numCols > 10) {
				throw new Exception();
			}
			matrixCalculator.beginMatrix1BuilderPanel(numRows, numCols);
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error");
			a.setHeaderText("Matrix dimensions must be integers between 1 and 12.");
			a.show();
		}
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	private void switchFocus(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER")) {
			if (t1.isFocused()) {
				t2.requestFocus();
			} else {
				t1.requestFocus();
			}
		} else if (e.getCode().toString().equals("LEFT")) {
			if (t2.isFocused()) {
				t1.requestFocus();
			}
		} else if (e.getCode().toString().equals("RIGHT")) {
			if (t1.isFocused()) {
				t2.requestFocus();
			}
		}
	}
}
