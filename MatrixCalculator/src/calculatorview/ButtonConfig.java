package calculatorview;

import javafx.scene.control.Button;

public class ButtonConfig {
	static String backgroundColor = "#3ea8a6";
	static String highlightColor = "#4dccc9";
	static String pressedColor = "#344f4e";
	
	private static void highlight(Button b) {
		b.setStyle("-fx-background-color: " + highlightColor + ";-fx-text-fill: white;-fx-border-color: black;-fx-border-width: 2;");
	}
	
	private static void deHighlight(Button b) {
		b.setStyle("-fx-background-color: " + backgroundColor + ";-fx-text-fill: white;-fx-border-color: black;-fx-border-width: 2;");
	}
	
	private static void mousePressed(Button b) {
		b.setStyle("-fx-background-color: " + pressedColor + ";-fx-text-fill: white;-fx-border-color: black;-fx-border-width: 2;");
	}
	
	public static void setEventHandlers(Button b) {
		b.setStyle("-fx-text-fill: white;-fx-background-color: " + backgroundColor + ";"
				+ "-fx-border-width: 2;-fx-border-color: black;");
		b.setOnMouseEntered(e -> highlight((Button) b));
		b.setOnMouseExited(e -> deHighlight((Button)b));
		b.setOnMousePressed(e -> mousePressed((Button)b));
		b.setOnMouseDragged(e -> mousePressed((Button)b));
		b.setOnMouseDragReleased(e -> deHighlight((Button)b));
		b.setOnMouseReleased(e -> deHighlight((Button)b));
	}
}
