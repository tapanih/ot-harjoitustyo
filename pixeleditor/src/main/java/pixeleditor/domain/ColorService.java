package pixeleditor.domain;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * An utility class that provides a global access to the currently selected color.
 */
public class ColorService {
    private static Color currentColor = Color.WHITE;
    private static ColorPicker colorPicker;

    public static void setColorPicker(ColorPicker picker) {
        colorPicker = picker;
    }  

    public static Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Updates the currently selected color.
     * @param color new color
     */
    public static void setCurrentColor(Color color) {
        currentColor = color;
        colorPicker.setValue(color);
    }  
}
