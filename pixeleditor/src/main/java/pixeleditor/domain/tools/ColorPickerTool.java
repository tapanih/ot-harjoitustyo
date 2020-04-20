package pixeleditor.domain.tools;

import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.CanvasService;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.Tool;

/**
 * A tool that sets current color to the color of clicked pixel.
 */
public class ColorPickerTool extends Tool {

    /**
     * A default constructor for ColorPickerTool.
     */
    public ColorPickerTool() {
        super("colorpicker.png");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pickColor(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pickColor(e);
    }

    private void pickColor(MouseEvent e) {
        PixelReader reader = CanvasService.getPixelReader(Color.TRANSPARENT);
        Color color = reader.getColor((int) e.getX(), (int) e.getY());
        if (color.equals(Color.TRANSPARENT)) {
            // Picking transparent color defaults to black.
            ColorService.setCurrentColor(Color.BLACK);
        } else {
            // Picked color is always fully opaque
            ColorService.setCurrentColor(Color.hsb(
                color.getHue(), color.getSaturation(), color.getBrightness())
            );
        }
    }
}
