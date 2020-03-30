package pixeleditor.domain.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.Tool;

/**
 * A pen tool for drawing individual pixels
 */
public class PenTool extends Tool {
    private int brushSize = 1;

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        PixelWriter pixelWriter = gc.getPixelWriter();
        pixelWriter.setColor((int) e.getX(), (int) e.getY(), Color.BLACK);
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        gc.setLineWidth(brushSize);
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        gc.beginPath(); // reset current path to empty
    }
    
}
