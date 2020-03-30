package pixeleditor.domain.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.Tool;

/**
 * An eraser tool for removing individual pixels
 */
public class EraserTool extends Tool {
    private int brushSize = 1;

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        PixelWriter pixelWriter = gc.getPixelWriter();
        pixelWriter.setColor((int) e.getX(), (int) e.getY(), Color.TRANSPARENT);
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        PixelWriter pixelWriter = gc.getPixelWriter();
        pixelWriter.setColor((int) e.getX(), (int) e.getY(), Color.TRANSPARENT);
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
    }
    
}
