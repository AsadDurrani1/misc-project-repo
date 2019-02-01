package calculatorview;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldConfig {
	public static void createLengthLimiter(TextField t, int maxTextLength) {
		t.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (t.getText().length() > maxTextLength) {
					t.setText(t.getText().substring(0, maxTextLength));
				}
			}
		});	
	}
}
