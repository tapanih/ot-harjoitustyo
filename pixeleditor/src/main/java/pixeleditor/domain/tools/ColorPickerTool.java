package pixeleditor.domain.tools;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.Tool;

public class ColorPickerTool extends Tool {

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        pickColor(gc, e);
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        pickColor(gc, e);
    }

    private void pickColor(GraphicsContext gc, MouseEvent e) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.BLACK);
        PixelReader pixelReader = gc.getCanvas().snapshot(params, null).getPixelReader();
        Color color = pixelReader.getColor((int) e.getX(), (int) e.getY());
        ColorService.setCurrentColor(color);
    }
    
}
