package pixeleditor.ui;

import javafx.scene.control.ColorPicker;
import pixeleditor.domain.ColorService;

public class ColorChooser extends ColorPicker {

    /**
     * Creates a color picker with a button that has custom styling.
     * It is also added to the ColorService to keep
     * the currently selected color updated.
     */
    public ColorChooser() {
        super();
        this.getStyleClass().add("button");
        this.setMaxWidth(27);
        this.setStyle("-fx-color-label-visible: false;");        
        init();
    }   

    private void init() {
        this.setOnAction(e -> {
            ColorService.setCurrentColor(this.getValue());
        });
        ColorService.setColorPicker(this);
    }
}
