package pixeleditor.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pixeleditor.domain.Tool;

public class ToolButton extends ToggleButton {

    ToolButton(Tool tool) {
        super();
        this.setUserData(tool);
        Image image = new Image("file:src/main/resources/images/" + tool.getIconFileName());
        this.setGraphic(new ImageView(image));
        this.setPadding(new Insets(2, 2, 2, 2));
    }
}
