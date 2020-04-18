package pixeleditor.domain.tools;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.Tool;

/**
 * A pen tool for drawing individual pixels
 */
public class PenTool extends Tool {
    protected Point2D prevMouseLocation = null;
    
    protected void drawLine(GraphicsContext gc, int x0, int y0, int x1, int y1, Color color) {
        int dx = Math.abs(x1 - x0),  sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0), sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;
        
        while (true) {
            gc.getPixelWriter().setColor(x0, y0, color);
            if (x0 == x1 && y0 == y1) {
                break;
            }
            int e2 = 2 * err;
            if (e2 >= dy) {
                err += dy;
                x0 += sx;
            }
            if (e2 <= dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        gc.getPixelWriter().setColor((int) e.getX(), (int) e.getY(), ColorService.getCurrentColor());
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        if (prevMouseLocation != null) {
            drawLine(gc, (int) prevMouseLocation.getX(), (int) prevMouseLocation.getY(),
                    (int) e.getX(), (int) e.getY(), ColorService.getCurrentColor());
        }
        prevMouseLocation = new Point2D(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        prevMouseLocation = null;
    }
}
