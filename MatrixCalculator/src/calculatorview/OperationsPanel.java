package calculatorview;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OperationsPanel extends VBox {
	private MatrixCalculator matrixCalculator;
	private MatrixBuilderPanel matrixBuilderPanel;
	private String fontFamily;
	
	public OperationsPanel(MatrixCalculator matrixCalculator) {
		this.matrixCalculator = matrixCalculator;
		this.matrixBuilderPanel = matrixCalculator.getMatrixBuilderPanel();
		this.fontFamily = matrixCalculator.getFontFamily();
		this.initGUI();	
	}
	
	public void initGUI() {
		Button additionButton = new Button("Addition");
		additionButton.setOnAction(e -> add());
		this.getChildren().add(additionButton);
		
		Button scalarMultiplyButton = new Button("Scalar Multiplication");
		scalarMultiplyButton.setOnAction(e -> scalarMultiply());
		this.getChildren().add(scalarMultiplyButton);
		
		Button matrixMultiplyButton = new Button("Matrix Multiplication");
		matrixMultiplyButton.setOnAction(e -> matrixMultiply());
		this.getChildren().add(matrixMultiplyButton);
		
		Button RREFButton = new Button("Row-Reduce");
		RREFButton.setOnAction(e -> RREF());
		this.getChildren().add(RREFButton);
		
		Button transposeButton = new Button("Transpose");
		transposeButton.setOnAction(e -> transpose());
		this.getChildren().add(transposeButton);
		
		Button rankButton = new Button("Rank");
		rankButton.setOnAction(e -> rank());
		this.getChildren().add(rankButton);
		
		Button determinantButton = new Button("Determinant");
		determinantButton.setOnAction(e -> determinant());
		this.getChildren().add(determinantButton);
		
		Button inverseButton = new Button("Inverse");
		inverseButton.setOnAction(e -> inverse());
		this.getChildren().add(inverseButton);
		
		Button powButton = new Button("Matrix Exponentiation");
		powButton.setOnAction(e -> pow());
		this.getChildren().add(powButton);
		this.matrixCalculator.getScene().widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				for (Object b: getChildren().toArray()) {
					((Button)b).setPrefWidth(newValue.intValue() / 4);
					((Button)b).setFont(new Font(fontFamily, newValue.intValue()*0.021));
				}
			}
		});
		this.matrixCalculator.getScene().heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				for (Object b: getChildren().toArray()) {
					((Button)b).setPrefHeight(newValue.intValue() /(getChildren().size()+1));
					((Button)b).setMaxHeight(newValue.intValue() /(getChildren().size()+2));
				}
				setPrefHeight(newValue.intValue() - 40);
			}
		});
		for (Object b: this.getChildren()) {
			int width = (int)(matrixCalculator.getScene().getWidth());
			int height = (int)(matrixCalculator.getScene().getHeight());
			((Button)b).setPrefWidth(width / 4);
			((Button)b).setFont(new Font(fontFamily, width*0.021));
			((Button)b).setPrefHeight(height /(getChildren().size()+1));
			((Button)b).setMaxHeight(height /(getChildren().size()+2));
			ButtonConfig.setEventHandlers((Button) b);
		}
	}
	
	private void add() {
		try {
			matrixBuilderPanel.updateMatrix();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error");
			a.setHeaderText(matrixBuilderPanel.getErrorMessage());
			a.show();
			return;
		}
		matrixCalculator.setOperation("Addition");
		matrixCalculator.setMode("Build Matrix 2");
		matrixCalculator.beginMatrix2BuilderPanel(matrixCalculator.getMatrix1().numRows, matrixCalculator.getMatrix1().numCols);
	}
	
	private void scalarMultiply() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}	
	}
	
	private void matrixMultiply() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
	
	private void RREF() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
	
	private void transpose() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
	
	
	private void rank() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
	
	private void determinant() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
	
	private void inverse() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
	
	private void pow() {
		if (this.matrixCalculator.getMatrix1() == null) {
			System.out.println("error");
		}
	}
}