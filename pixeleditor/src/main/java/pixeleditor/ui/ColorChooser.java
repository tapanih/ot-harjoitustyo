package pixeleditor.ui;

import javafx.scene.control.ColorPicker;
import pixeleditor.domain.ColorService;

/**
 * A subclass of ColorPicker with custom styling that is also connected to the ColorService.
 */
public class ColorChooser extends ColorPicker {

    /**
     * Creates a color picker with a button that has custom styling.
     * It is also added to the ColorService to keep
     * the currently selected color updated.
     */
    public ColorChooser() {
        super();
        this.getStyleClass().add("button");
        init();
    }   

    private void init() {
        this.setOnAction(e -> {
            ColorService.setCurrentColor(this.getValue());
        });
        ColorService.setColorPicker(this);
    }
}
