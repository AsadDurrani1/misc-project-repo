package calculatorview;

import matrixmodel.*;

import javafx.application.Application;
import javafx.beans.InvalidationListener;

import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MatrixCalculator extends Application {
	private int WIDTH = 700;
	private int HEIGHT = 500;
	private Matrix Matrix1;
	private Matrix Matrix2;
	private Scene scene;
	private Stage primaryStage;
	private OperationsPanel operationsPanel;
	private DimensionsPanel dimensionsPanel;
	private MatrixBuilderPanel matrixBuilderPanel;
	private CalculateButton calculateButtonPanel;
	private BorderPane root;
	private Label topMessage;
	private String fontFamily = "Agency FB Bold";
	private String currentMode = "Pick Dimension Matrix 1";
	private String operation = "";
	private static Hashtable<String, String> modesToMessages = new Hashtable<String, String>() {
		private static final long serialVersionUID = 1L;

	{
		put("Pick Dimension Matrix 1", "Enter the dimensions of your matrix.");
		put("Pick Dimension Matrix 2", "Enter the dimensions of your matrix.");
		put("Pick Dimension Matrix 2 (Multiplication)", "Enter the dimensions of your matrix.");
		put("Build Matrix 1", "Enter your matrix, using the arrow keys to navigate. \n"
				+ "When you're done, choose an operation from the right.");
		put("Build Matrix 2", "Enter the entries of your second matrix,\n using the arrow keys to navigate.");
		put("Show Result", "Result: ");
	}};
	
	public MatrixCalculator() {
		super();
		this.Matrix1 = null;
		this.Matrix2 = null;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.root = new BorderPane();
		this.scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
		root.setRight(operationsPanel);
		
		initGUI();
		
		//add width and height listeners on scene to allow dynamic resizing of all nodes
		ChangeListener<Number> widthListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				topMessage.setPrefWidth(newValue.intValue());
				topMessage.setFont(new Font("Agency FB Bold", scene.getWidth()*0.04));
			}
		};
		scene.widthProperty().addListener(widthListener);
		ChangeListener<Number> heightListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//Put in here whatever you want to happen when the window height changes
			}
		};
		scene.heightProperty().addListener(heightListener);
		
		//Configure and show the stage
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		primaryStage.setTitle("Matrix Calculator");
		primaryStage.setMinWidth(scene.getWidth() + 40);
		primaryStage.setMinHeight(scene.getHeight() + 40);
		primaryStage.show();
	}
	
	private void initGUI() {
		this.topMessage = new Label(modesToMessages.get(currentMode)) {{
			setStyle(";-fx-background-color: #3ea8a6; -fx-text-fill: white;-fx-border-width: 2;-fx-border-color: black;");
			setFont(new Font(fontFamily, scene.getWidth()*0.04));
			this.setAlignment(Pos.CENTER);
			setPrefWidth((int)scene.getWidth());
		}};
		root.setTop(topMessage);
		if (currentMode == "Pick Dimension Matrix 1") {
			this.dimensionsPanel = new DimensionsPanel(this);
			root.setCenter(dimensionsPanel);
		}
	}
	
	public String getMode() {
		return currentMode;
	}
	
	public void setMode(String mode) {
		this.currentMode = mode;
	}

	public Matrix getMatrix1() {
		return Matrix1;
	}
	
	public Matrix getMatrix2() {
		return Matrix2;
	}
	
	public void setMatrix1(Matrix matrix) {
		this.Matrix1 = matrix;
	}
	
	public void setMatrix2(Matrix matrix) {
		this.Matrix2 = matrix;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public DimensionsPanel getDimensionsPanel() {
		return dimensionsPanel;
	}
	
	public OperationsPanel getOperationsPanel() {
		return operationsPanel;
	}
	
	public MatrixBuilderPanel getMatrixBuilderPanel() { 
		return matrixBuilderPanel;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public String getFontFamily() {
		return fontFamily;
	}
	
	public void beginMatrix1BuilderPanel(int numRows, int numCols) {
		setMode("Build Matrix 1");
		this.matrixBuilderPanel = new MatrixBuilderPanel(this, numRows, numCols);
		this.operationsPanel = new OperationsPanel(this);
		root.setCenter(matrixBuilderPanel);
		root.setRight(operationsPanel);
		this.dimensionsPanel = null;
		initGUI();
	}

	public void beginMatrix2BuilderPanel(int numRows, int numCols) {
		setMode("Build Matrix 2");
		this.matrixBuilderPanel = new MatrixBuilderPanel(this, numRows, numCols);
		this.operationsPanel = null;
		this.calculateButtonPanel = new CalculateButton(this);
		root.setCenter(matrixBuilderPanel);
		root.setRight(calculateButtonPanel);
		this.dimensionsPanel = null;
		initGUI();
	}

	public void beginResultsScreen() {
		if (operation.equals("Addition") && Matrix1 != null && Matrix2 != null) {
			try {
				System.out.println(Matrix1.add(Matrix2));
			} catch (MatrixException e) {}
		}
	}
}