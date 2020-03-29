package pixeleditor.domain.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pixeleditor.domain.Tool;

/**
 * A pen tool for drawing individual pixels
 */
public class PenTool extends Tool {
    private int brushSize = 2;

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        gc.fillRect(e.getX(), e.getY(), brushSize, brushSize);
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        gc.beginPath(); // reset current path to empty
    }
    
}
