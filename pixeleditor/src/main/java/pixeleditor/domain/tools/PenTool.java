package pixeleditor.domain.tools;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixeleditor.domain.ColorService;
import pixeleditor.domain.Tool;

/**
 * A pen tool for coloring pixels with the currently selected color.
 */
public class PenTool extends Tool {
    protected Point2D prevMouseLocation = null;

    /**
     * A default constructor for PenTool.
     */
    public PenTool() {
        super("pen.png");
    }

    /**
     * A constructor for subclasses that acts as a pass-through to the superclass constructor.
     * @param iconFileName name of the image file located in resources/images that's to be used as an icon
     */
    protected PenTool(String iconFileName) {
        super(iconFileName);
    }

    /**
     * Draws a line using Bresenham's line algorithm.
     * Source: https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
     * @param gc    GraphicsContext for updating the canvas
     * @param x0    x-coordinate of the start of the line
     * @param y0    y-coordinate of the start of the line
     * @param x1    x-coordinate of the end of the line
     * @param y1    y-coordinate of the end of the line
     * @param color color of the drawn line
     */
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
