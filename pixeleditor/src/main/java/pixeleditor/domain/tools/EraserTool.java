package pixeleditor.domain.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pixeleditor.domain.Tool;

/**
 * An eraser tool for removing pixels
 */
public class EraserTool extends Tool {
    private int brushSize = 2;

    @Override
    public void mousePressed(GraphicsContext gc, MouseEvent e) {
        gc.clearRect(e.getX(), e.getY(), brushSize, brushSize);
    }

    @Override
    public void mouseDragged(GraphicsContext gc, MouseEvent e) {
        gc.clearRect(e.getX(), e.getY(), brushSize, brushSize);
    }

    @Override
    public void mouseReleased(GraphicsContext gc, MouseEvent e) {
        gc.beginPath(); // reset current path to empty
    }
    
}
