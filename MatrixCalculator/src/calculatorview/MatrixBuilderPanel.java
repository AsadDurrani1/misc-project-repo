package calculatorview;

import matrixmodel.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MatrixBuilderPanel extends GridPane {
	private MatrixCalculator matrixCalculator;
	private Matrix matrix;
	private TextField[][] textFieldMatrix;
	private String errorMessage = "Entries of Matrix must be integers or decimals.";
	private static final int maxTextLength = 4;
	private OperationsPanel operationsPanel;
	
	public MatrixBuilderPanel(MatrixCalculator matrixCalculator, int m, int n) {
		this.matrixCalculator = matrixCalculator;
		if (m == n) {
			this.matrix = MatrixFactory.createSquareZeroMatrix(m);
		} else {
			this.matrix = MatrixFactory.createZeroMatrix(m, n);
		}
		if (matrixCalculator.getMode().equals("Build Matrix 1")) { 
			this.matrixCalculator.setMatrix1(this.matrix);
		} else {
			this.matrixCalculator.setMatrix2(this.matrix);
		}
		this.operationsPanel = matrixCalculator.getOperationsPanel();
		this.textFieldMatrix = new TextField[matrix.numRows][matrix.numCols];
		for (int i = 0; i < matrix.numRows; i++) {
			for (int j = 0; j < matrix.numCols; j++) {
				final int k = i;
				final int l = j;
				TextField t = new TextField();
				t.setAlignment(Pos.CENTER);
				t.setStyle("-fx-border-color: black; -fx-border-width: 1;");
				t.setMaxWidth(matrixCalculator.getScene().getWidth()/matrix.numCols);
				t.setPrefWidth(matrixCalculator.getScene().getWidth()/matrix.numCols);
				t.setFont(new Font("Calibri", matrixCalculator.getScene().getWidth()/matrix.numCols/5));
				t.setMaxHeight(matrixCalculator.getScene().getHeight()/(matrix.numRows+1));
				t.setPrefHeight(matrixCalculator.getScene().getHeight()/(matrix.numRows+1));
				TextFieldConfig.createLengthLimiter(t, maxTextLength);
				t.setOnKeyPressed(e -> keyPressed(e, k, l));
				matrixCalculator.getRoot().widthProperty().addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						t.setMaxWidth(newValue.intValue()/(matrix.numCols));
						t.setPrefWidth(newValue.intValue()/(matrix.numCols));
						t.setFont(new Font("Calibri", newValue.intValue()/matrix.numCols/5));
					}
				});
				matrixCalculator.getRoot().heightProperty().addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						t.setMaxHeight(newValue.intValue()/(matrix.numRows+1));
						t.setPrefHeight(newValue.intValue()/(matrix.numRows+1));
						setMaxHeight(5*newValue.intValue()/6);
					}
				});
				textFieldMatrix[i][j] = t;
				this.add(textFieldMatrix[i][j], j, i);
			}
		}
	}

	private void keyPressed(KeyEvent e, int i, int j) {
		if (e.getCode().toString().equals("ENTER")) {
			if (j < matrix.numCols - 1) {
				getTextField(i, j+1).requestFocus();
			} else if (i < matrix.numRows - 1) {
				getTextField(i+1, 0).requestFocus();
			}
		} else if (e.getCode().toString().equals("LEFT")) {
			if (j > 0) {
				getTextField(i, j-1).requestFocus();
			}
		} else if (e.getCode().toString().equals("UP")) {
			if (i > 0) {
				getTextField(i-1, j).requestFocus();
			}
		} else if (e.getCode().toString().equals("RIGHT")) {
			if (j < matrix.numCols - 1) {
				getTextField(i, j+1).requestFocus();
			}
		} else if (e.getCode().toString().equals("DOWN")) {
			if (i < matrix.numRows - 1) {
				getTextField(i+1, j).requestFocus();
			}
		}
	}
	
	public TextField getTextField(int i, int j) {
		return textFieldMatrix[i][j];
	}
	

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void updateMatrix() {
		for (int i = 0; i < matrix.numRows; i++) {
			for (int j = 0; j < matrix.numCols; j++) {
				String text = getTextField(i, j).getText();
				double d;
				if (text.equals("")) {
					d = 0;
				} else {
					d = Double.parseDouble(getTextField(i, j).getText());
				}
				matrix.setIndex(i, j, d);
			}
		}
	}
}