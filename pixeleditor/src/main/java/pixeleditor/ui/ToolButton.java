package pixeleditor.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pixeleditor.domain.Tool;

/**
 * A button element that is connected to a tool.
 */
public class ToolButton extends ToggleButton {

    ToolButton(Tool tool) {
        super();
        this.setUserData(tool);
        Image image = new Image("/images/" + tool.getIconFileName());
        this.setGraphic(new ImageView(image));
        this.setPadding(new Insets(2, 2, 2, 2));
    }
}
