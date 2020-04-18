package pixeleditor.domain;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorService {
    private static Color currentColor;
    private static ColorPicker colorPicker;

    public static void setColorPicker(ColorPicker picker) {
        colorPicker = picker;
    }  

    public static Color getCurrentColor() {
        return currentColor;
    }

    public static void setCurrentColor(Color color) {
        currentColor = color;
        colorPicker.setValue(color);
    }  
}
