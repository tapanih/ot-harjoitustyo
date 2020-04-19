package pixeleditor.domain.tools;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.CanvasService;

/**
 * An eraser tool for removing individual pixels.
 */
public class EraserTool extends PenTool {

    /**
     * A default constructor for EraserTool.
     */
    public EraserTool() {
        super("eraser.png");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        CanvasService.getPixelWriter().setColor((int) e.getX(), (int) e.getY(), Color.TRANSPARENT);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (prevMouseLocation != null) {
            drawLine((int) prevMouseLocation.getX(), (int) prevMouseLocation.getY(),
                    (int) e.getX(), (int) e.getY(), Color.TRANSPARENT);
        }
        prevMouseLocation = new Point2D(e.getX(), e.getY());
    }
}
