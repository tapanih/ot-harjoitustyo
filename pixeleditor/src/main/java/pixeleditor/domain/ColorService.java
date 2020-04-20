package pixeleditor.domain;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * An utility class that provides a global access to the currently selected color.
 */
public class ColorService {
    private static Color currentColor = Color.WHITE;
    private static ColorPicker picker;

    /**
     * Sets the ColorPicker element that should be updated when color is picked
     * through other means (f.ex. with the color picker tool).
     * @param colorPicker
     */
    public static void setColorPicker(ColorPicker colorPicker) {
        picker = colorPicker;
    }  

    /**
     * Returns the currently selected color.
     * @return current color
     */
    public static Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Updates the currently selected color.
     * @param color new color
     */
    public static void setCurrentColor(Color color) {
        currentColor = color;
        picker.setValue(color);
    }
}
