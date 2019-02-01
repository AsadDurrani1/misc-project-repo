package ca.utoronto.utm.paint;

import javafx.application.Application;
import javafx.stage.Stage;

/** The Paint class serves as the main class of the paint package. It begins by initializing a PaintModel and View, 
 * and launching a window displaying the View which uses the PaintModel as reference for updates. See the doc
 * for PaintModel and View to see how these two interact within the window.
 * 
 */
public class Paint extends Application {

	PaintModel model; // Model
	View view; // View + Controller

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		this.model = new PaintModel();
		
		// View + Controller
		this.view = new View(model, stage);
	}
}
