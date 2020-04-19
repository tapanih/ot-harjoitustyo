package pixeleditor.domain.tools;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * An eraser tool for removing individual pixels.
 */
public class EraserTool extends PenTool {

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        gc.getPixelWriter().setColor((int) e.getX(), (int) e.getY(), Color.TRANSPARENT);
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        if (prevMouseLocation != null) {
            drawLine(gc, (int) prevMouseLocation.getX(), (int) prevMouseLocation.getY(),
                    (int) e.getX(), (int) e.getY(), Color.TRANSPARENT);
        }
        prevMouseLocation = new Point2D(e.getX(), e.getY());
    }
}
