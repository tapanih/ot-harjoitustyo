package pixeleditor.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolButton extends ToggleButton {

    ToolButton(String fileName) {
        super();
        Image image = new Image("file:src/main/resources/images/" + fileName);
        this.setGraphic(new ImageView(image));
        this.setPadding(new Insets(2, 2, 2, 2));
    }
}
