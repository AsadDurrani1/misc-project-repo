package ca.utoronto.utm.paint;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** Acts as the containing class for all major classes in the Paint application. Has a PaintModel, a PaintPanel, and the four 
 * mode selector Panels that interact with PaintPanel. (See doc for these classes). This class also has a menu bar that interacts
 * directly with its PaintModel, to do actions like undo, redo, new, and exit.
 */
public class View implements EventHandler<ActionEvent> {

	private PaintModel model;

	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;
	private ColourChooserPanel colourChooserPanel;
	private FillStyleSelectorPanel fillStyleSelectorPanel;
	private LineThicknessPanel lineThicknessPanel;

	public View(PaintModel model, Stage stage) {

		this.model = model;
		initUI(stage);
	}

	private void initUI(Stage stage) {

		this.paintPanel = new PaintPanel(this.model, this);
		this.shapeChooserPanel = new ShapeChooserPanel(this);
		this.colourChooserPanel = new ColourChooserPanel(this);
		this.fillStyleSelectorPanel = new FillStyleSelectorPanel(this);
		this.lineThicknessPanel = new LineThicknessPanel(this);

		GridPane root = new GridPane();
		root.setMinSize(200, 475);
		
		root.add(createMenuBar(), 0, 0, 4, 1);
		root.add(this.shapeChooserPanel, 0, 1);
		root.add(this.colourChooserPanel, 1, 1);
		root.add(this.paintPanel, 2, 1, 2, 3);
		root.add(this.fillStyleSelectorPanel, 0, 2, 2, 1);
		root.add(this.lineThicknessPanel, 0, 3, 2, 1);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Paint");
		stage.show();

		/**
		 * ChangeListener listens for changes in window width and modifies canvas appropriately.
		 */
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				if (newSceneWidth.doubleValue() < 200) {
					stage.setWidth(200);
				} else {
					passNewSceneWidth(newSceneWidth.doubleValue());
					stage.show();
				}
			}
		});
		
		/**
		 * ChangeListener listens for changes in window height and modifies canvas appropriately.
		 */
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				if (newSceneHeight.doubleValue() < 475) {
					stage.setHeight(475);
				} else {
					passNewSceneHeight(newSceneHeight.doubleValue());
					stage.show();
				}
			}
		});
	}
	
	public void passNewSceneWidth(double width) {
		this.paintPanel.setCanvasWidth(width);
	}
	
	public void passNewSceneHeight(double height) {
		this.paintPanel.setCanvasHeight(height);
	}

	public PaintPanel getPaintPanel() {
		return paintPanel;
	}

	public ShapeChooserPanel getShapeChooserPanel() {
		return shapeChooserPanel;
	}
	
	public LineThicknessPanel getLineThicknessPanel() {
		return lineThicknessPanel;
	}

	private MenuBar createMenuBar() {

		MenuBar menuBar = new MenuBar();
		Menu menu;
		MenuItem menuItem;

		// A menu for File

		menu = new Menu("File");

		menuItem = new MenuItem("New");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Open");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Save");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menu.getItems().add(new SeparatorMenuItem());

		menuItem = new MenuItem("Exit");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuBar.getMenus().add(menu);

		// Another menu for Edit

		menu = new Menu("Edit");

		menuItem = new MenuItem("Cut");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Copy");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Paste");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menu.getItems().add(new SeparatorMenuItem());

		menuItem = new MenuItem("Undo");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuItem = new MenuItem("Redo");
		menuItem.setOnAction(this);
		menu.getItems().add(menuItem);

		menuBar.getMenus().add(menu);

		return menuBar;
	}

	@Override
	public void handle(ActionEvent event) {
		System.out.println(((MenuItem)event.getSource()).getText());
		String command = ((MenuItem)event.getSource()).getText();
		
		if(command == "New") {
			this.model.clearArray();
		}if(command == "Undo") {
			this.model.undo();
		}if(command == "Redo") {
			this.model.redo();
		}if(command == "Exit") {
			Platform.exit();
		}
	}
}
